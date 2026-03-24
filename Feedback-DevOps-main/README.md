# Student Feedback Form (Symbiosis Admission Form)

This project contains a web-based admission and feedback form with client-side validation, plus a Selenium automation suite for end-to-end checks.

## Project Structure

```
StudentFeedbackForm/
  web/
    index.html
    assets/
      css/
        style.css
      js/
        validate.js
  selenium-tests/
    src/
      test/
        pom.xml
        src/main/java/com/selenium/test/FormTest.java
```

## Features

- Name, Email, Mobile Number, Password, and Confirm Password fields
- Gender radio buttons (Male, Female, Other)
- Course dropdown (B.Tech, BBA, BCA, MBA)
- Feedback Comments field
- Register submit button
- Reset button with id `resetBtn`

## Validation Rules

Validation is implemented in `web/assets/js/validate.js`.

- Name cannot be empty
- Email cannot be empty
- Mobile number must be exactly 10 digits (when provided)
- Password must be at least 6 characters
- Password and Confirm Password must match
- Feedback must contain at least 10 words

Valid submission alert:

- `Registration Successful`

## Selenium Test Coverage

Automation is in `selenium-tests/src/test/src/main/java/com/selenium/test/FormTest.java`.

Included test cases:

- TC01 Title check
- TC02 Empty Name validation
- TC03 Empty Email validation
- TC04 Short Password validation
- TC05 Password mismatch validation
- TC06 Gender radio behavior
- TC07 Course dropdown selection
- TC08 Valid submission success
- TC09 Reset button clears field
- TC10 Invalid mobile validation
- TC11 Short feedback validation
- TC12 Valid feedback submission success

## Run Instructions

### Manual Run

Open `web/index.html` in a browser.

### Selenium Run (Maven)

Prerequisites:

- Java 17+
- Maven 3.8+
- Google Chrome

From project root:

```powershell
cd selenium-tests/src/test
mvn test
```

## Notes

- `FormTest` resolves `web/index.html` dynamically by searching parent folders, so folder-name changes should still work if the `web/index.html` path exists above the module.
- If Maven is not installed, you can still perform manual validation by opening the form in a browser.
