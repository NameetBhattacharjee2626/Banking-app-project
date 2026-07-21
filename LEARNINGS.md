# Banking App — Learnings & Decisions Log

> Purpose: record *why* decisions were made at each stage, not a line-by-line diary.
> Test for an entry: "if I only read this in 6 months, would I understand the decision without re-deriving it?"

---

## Stage 1 — Console App (single account, no OOP)

### Decisions
- Single `double balance` declared in `main`; no persistence — data resets every run. Acceptable because no storage mechanism exists yet (that's Stage 5's job).
- Used `do-while` loop for the menu — menu must display at least once before checking the exit condition.
- Used `switch` expressions (`->`) instead of `if/else` chains for menu routing — cleaner, no fallthrough risk.
- `deposit`/`withdrawal` are static methods that take `balance` as a parameter and `return` the new value. Required because Java passes primitives **by value** — a method can't mutate the caller's variable directly, only hand back a new value that the caller must reassign (`balance = deposit(amount, balance);`).
- Input validation (amount > 0, sufficient funds) lives in `main`, **before** calling `deposit`/`withdraw` — not inside those methods. Reason: keeps the methods doing one job (compute new balance), avoids ambiguous fake return values for "invalid input," and keeps error messages under `main`'s control.

### Bugs hit and why
- `balance =+ amount` instead of `balance += amount`. `=+` is parsed as assigning the *unary plus* of `amount` — silently overwrites balance every call instead of adding to it. Classic typo that compiles with zero warnings. Lesson: always trace execution by hand when output looks "almost right."
- `printf` format string had `\n` before the `$`, producing a stray line break mid-output. Fixed order to `"$%.2f\n"`.
- Naming convention slips: `ShowBalance` (PascalCase) should be `showBalance` (camelCase) — Java convention reserves capital-first names for classes/types, lowercase-first for methods/variables. Also renamed parameters that collided with method names (`deposit(deposit, balance)` → `deposit(amount, balance)`).

### Known debt at end of Stage 1
- None — scope was small and fully met.

---

## Stage 2 — Multiple Accounts / Transfers (conceptual, not built with primitives)

### Decisions
- Deliberately **skipped** building Stage 2 with raw primitives (`balance1`, `balance2`, ...). Reasoned through, on paper, why it breaks:
  - No way to "look up" an account by number without a giant `if/else` chain per operation (deposit/withdraw/balance × N accounts = unmanageable duplication).
  - No natural way to loop through "all accounts" since they're separate, unrelated variable names, not a collection.
- Collapsed Stage 2 into Stage 3 (OOP) as a conscious tradeoff: faster to reach real design patterns, at the cost of not viscerally suffering through the primitive version. Paid a small "toll" — wrote out pseudocode for a 2-account transfer by hand first, to at least feel the shape of the problem before skipping to classes.
- Recognized during pseudocode exercise: a transfer (subtract from A, add to B) is not naturally atomic — if something fails between the two steps, money could vanish. Not solvable yet (needs later stages — concurrency/transactions), but flagged as a real concern for later, not ignored.

### Known debt at end of Stage 2
- Transfer atomicity not handled — revisit when concurrency (Stage 8) or DB transactions (Stage 7) are covered.
- No menu loop rebuilt yet around objects (Stage 1's loop was primitive-based and got set aside when moving to OOP).

---

## Stage 3 — Proper OOP (`BankAccount` class)

### Decisions
- `BankAccount` fields (`balance`, `owner`, `accountNumber`) are **all `private`**. Reason: public fields let any outside code bypass validation entirely (e.g. `account.balance = -999` skips every check ever written). Encapsulation means the class is the *only* thing allowed to touch its own raw data; everything else goes through public methods.
- Validation logic (amount > 0, sufficient funds) now lives **inside** `deposit`/`withdraw`/`transferTo` themselves, since those are instance methods operating directly on the object's own private field — different from Stage 1, where validation had to live in `main` because the static methods had no privileged access to a private field.
- Getters (`getBalanceValue`, `getOwner`, `getAccountNumber`) take **no parameters** — an instance method automatically knows which object it's running against via the implicit `this`, determined by whatever object name appears *before the dot* at the call site (`account2.deposit(100)` → `this` = `account2` for that call). No need to pass the object in as an argument.
- Added a constructor (`BankAccount(owner, accountNumber, startingBalance)`) so accounts are never created in an incomplete/default state (`new BankAccount()` previously left `owner = null`, `accountNumber = 0`, `balance = 0` silently).
- Wrote a self-checking test in `main` (hand-computed expected balances, `if` check for pass/fail) instead of just printing values and eyeballing them — catches regressions automatically instead of relying on manual inspection every time.
- Considered `BigDecimal` for currency precision instead of `double`. Deliberately deferred: `BigDecimal` is immutable (arithmetic via `.add()`, not `+=`), requires `.compareTo()` instead of `<`/`>`, and has construction gotchas (never construct from a `double` literal directly). Decided this is a good **later, dedicated refactor** once more comfortable with immutability and exception handling, rather than adding a second hard concept while OOP fundamentals are still being cemented.

### Bugs hit and why
- `withdraw` initially had no `return;` after the error-print branches — code fell through and executed `balance -= amount` even on invalid/insufficient-funds input, silently corrupting balance (including making it go negative, or "add" money via a negative withdrawal). Root cause: copied the validate-then-act shape without tracing execution path, same failure mode as the earlier `=+` bug just relocated.
- Fields were left at default (package-private) access instead of `private` at first — looks protected, isn't. Default access still allows same-package code to read/write directly.
- Getters were first written taking a `BankAccount` parameter and declaring unused local variables (`void getBalance(BankAccount thisBankAccount) { double balance; }`) — mixing the Stage 1 static-method mental model (data passed in explicitly) with the instance-method model (data already belongs to the object). Corrected to plain `public double getBalance() { return balance; }` with no parameters.
- Account number passed as `03012` — leading zero makes Java interpret an int literal as **octal**, silently giving a different decimal value than intended. Never use leading zeros on int literals unless octal is actually wanted.
- Changed starting balances mid-test without recomputing expected values by hand — test started reporting "failed" on code that was actually correct. Lesson: any time test inputs change, expected outputs must be recalculated, not left stale — a wrong test is worse than no test, because it teaches you to distrust correct code.

### Known debt at end of Stage 3
- Transaction history field still not implemented (scoped as required back in Stage 2 design, never added to `BankAccount`).
- No `Bank` class yet to hold/coordinate multiple accounts (needed for transfers between arbitrary accounts, lookup by account number).
- No menu loop rebuilt around objects — `main` currently just hardcodes two accounts and runs fixed test calls, no interactive flow.
- Balance stored as `double`, not precision-safe for currency — `BigDecimal` refactor deferred intentionally (see decisions above).
- No collection type yet for storing multiple accounts (arrays vs. `ArrayList` decision still open, pending Stage 4).
