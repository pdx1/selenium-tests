package com.wikia.webdriver.pageobjectsfactory.pageobject.special.editaccount;

import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.BasePageObject;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Karol 'kkarolk' Kujawiak
 */
public class EditAccount extends BasePageObject {

  @FindBy(css = "[name=wpUserName]")
  private WebElement userNameField;
  @FindBy(css = "[value='Close account']")
  private WebElement closeAccountButton;
  @FindBy(css = "#wpReason")
  private WebElement closeResonField;
  @FindBy(css = "#wpActionSetPass")
  private WebElement newPasswordRadio;
  @FindBy(css = "[name=wpNewPass]")
  private WebElement newPasswordField;
  @FindBy(css = "[value='Clear disable flag']")
  private WebElement clearDisableFlagButton;
  @FindBy(css = "fieldset > span")
  private WebElement statusMessage;

  private static final String
      USER_ACCOUNT_REOPEN_MESSAGE =
      "Successfully removed disabled bit for account";
  private static final String USER_ACCOUNT_CLOSED_MESSAGE = "Successfully disabled account";

  public EditAccount(WebDriver driver, String communityWikiURL, String userName) {
    super(driver);

    driver.get(
        communityWikiURL +
        URLsContent.SPECIAL_EDIT_ACCOUNT
    );
      for(Cookie cookie : driver.manage().getCookies()){
          System.out.println(cookie.toString());
      }
    userNameField.sendKeys(userName);
    userNameField.submit();
    PageObjectLogging.log(
        "editAccount",
        URLsContent.SPECIAL_EDIT_ACCOUNT + " page opened",
        true
    );
  }

  public void closeAccount(String reason) {
    scrollAndClick(closeAccountButton);
    closeResonField.sendKeys(reason);
    closeResonField.submit();
    PageObjectLogging.log(
        "closeAccount",
        "account closed",
        true
    );
  }

  public void verifyAccountClosedMessage() {
    waitForTextToBePresentInElementByElement(
        statusMessage,
        USER_ACCOUNT_CLOSED_MESSAGE
    );
    PageObjectLogging.log(
        "verifyAccountClosedMessage",
        "verified account closed",
        true
    );
  }

  public void reopenAccount(String newPassword) {
    newPasswordRadio.click();
    newPasswordField.sendKeys(newPassword);
    newPasswordField.submit();
    scrollAndClick(clearDisableFlagButton);
    PageObjectLogging.log(
        "reopenAccount",
        "account reopened",
        true
    );
  }

  public void verifyAccountReopenedMessage() {
    waitForTextToBePresentInElementByElement(
        statusMessage,
        USER_ACCOUNT_REOPEN_MESSAGE
    );
    PageObjectLogging.log(
        "verifyAccountReopenedMessage",
        "verified account reopened",
        true
    );
  }
}
