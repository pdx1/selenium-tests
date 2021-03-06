package com.wikia.webdriver.pageobjectsfactory.componentobject.visualeditordialogs;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class VisualEditorAddMediaDialog extends VisualEditorDialog {

  @FindBy(css = ".oo-ui-textInputWidget input")
  private WebElement searchInput;
  @FindBy(css = ".oo-ui-window-foot .oo-ui-labelElement-label")
  private WebElement addMediaButton;
  @FindBy(css = ".ve-ui-wikiaMediaQueryWidget-uploadWrapper .oo-ui-labelElement-label")
  private WebElement topUploadButton;
  @FindBy(css = ".oo-ui-window-body")
  private WebElement mediaDialogBody;
  @FindBy(css = ".oo-ui-bookletLayout .ve-ui-wikiaUploadButtonWidget input")
  private WebElement fileUploadInput;
  @FindBy(css = ".oo-ui-fieldsetLayout input")
  private WebElement fileNameInput;
  @FindBy(css = ".ve-ui-wikiaMediaPageWidget-item-license select")
  private WebElement imageLicenseDropdown;

  private By mediaResultsWidgetBy = By.cssSelector(".ve-ui-wikiaMediaResultsWidget");
  private By mediaResultsBy = By.cssSelector(".ve-ui-mwMediaResultWidget");
  private By mediaAddIconBy = By.cssSelector(".oo-ui-icon-unchecked");
  private By
      mediaTitlesBy =
      By.cssSelector(".ve-ui-wikiaMediaResultsWidget .oo-ui-labelElement-label");

  public enum ImageLicense {
    NONESELECTED("None selected", ""),
    FAIRUSE("Fairuse", ""),
    SELF("Self", "This file was uploaded by the photographer or author."),
    FROMWIKIMEDIA("From Wikimedia",
                  "This file was originally uploaded on Wikipedia or another Wikimedia project."),
    CCBYSA("CC-BY-SA",
           "This file is licensed under the Creative Commons Attribution-Share Alike License"),
    OTHERFREE("Other free", "This file is licensed under a free license."),
    PD("PD", "This file is in the public domain"),
    PERMISSION("Permission",
               "This file is copyrighted. The copyright holder has given permission for its use.");

    private String displayName;
    private String displayText;

    ImageLicense(String displayName, String displayText) {
      this.displayName = displayName;
      this.displayText = displayText;
    }

    public String toString() {
      return displayName;
    }

    public String getText() {
      return displayText;
    }
  }

  public VisualEditorAddMediaDialog(WebDriver driver) {
    super(driver);
  }

  private void typeInSearchTextField(String input) {
    waitForElementByElement(searchInput);
    searchInput.sendKeys(input);
  }

  private void clickAddMediaButton() {
    waitForElementVisibleByElement(addMediaButton);
    addMediaButton.click();
  }

  public VisualEditorPageObject addMediaByURL(String url) {
    waitForDialogVisible();
    typeInSearchTextField(url);
    waitForElementVisibleByElement(topUploadButton);
    waitForElementClickableByElement(topUploadButton);
    clickAddMediaButton();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  public VisualEditorAddMediaDialog searchMedia(String searchText) {
    waitForDialogVisible();
    typeInSearchTextField(searchText);
    return new VisualEditorAddMediaDialog(driver);
  }

  public VisualEditorPageObject addExistingMedia(int number) {
    waitForDialogVisible();
    WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
    waitForElementVisibleByElement(mediaResultsWidget);
    List<WebElement> mediaResults = mediaResultsWidget.findElements(mediaResultsBy);
    for (int i = 0; i < number; i++) {
      WebElement mediaAddIcon = mediaResults.get(i).findElement(mediaAddIconBy);
      mediaAddIcon.click();
    }
    clickAddMediaButton();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  public VisualEditorPageObject uploadImage(String fileName) {
    waitForDialogVisible();
    selectFileToUpload(fileName);
    waitForElementVisibleByElement(topUploadButton);
    clickAddMediaButton();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  public VisualEditorPageObject uploadImage(String fileName, String newFileName) {
    waitForDialogVisible();
    selectFileToUpload(fileName);
    waitForElementVisibleByElement(topUploadButton);
    typeNewFileName(newFileName);
    clickAddMediaButton();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  public void selectImageLicense(ImageLicense imageLicense) {
    waitForElementVisibleByElement(imageLicenseDropdown);
    Select imageLicenseSelect = new Select(imageLicenseDropdown);
    imageLicenseSelect.selectByValue(imageLicense.toString());
    PageObjectLogging
        .log("selectImageLicense", "License: " + imageLicense.toString() + " selected", true);
  }

  public VisualEditorPageObject uploadImage(String fileName, String newFileName,
                                            ImageLicense imageLicense) {
    waitForDialogVisible();
    selectFileToUpload(fileName);
    waitForElementVisibleByElement(topUploadButton);
    typeNewFileName(newFileName);
    selectImageLicense(imageLicense);
    clickAddMediaButton();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  private void typeNewFileName(String newFileName) {
    waitForElementByElement(fileNameInput);
    if (Boolean.parseBoolean(fileNameInput.getAttribute("readonly"))) {
      throw new NoSuchElementException(
          "File name input is read only! This file already exist on this wiki");
    }
    fileNameInput.clear();
    fileNameInput.sendKeys(newFileName);
    waitForValueToBePresentInElementsAttributeByElement(fileNameInput, "value", newFileName);
  }

  public VisualEditorPageObject previewExistingMediaByIndex(int index) {
    waitForDialogVisible();
    WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
    waitForElementVisibleByElement(mediaResultsWidget);
    WebElement targetMedia = mediaResultsWidget.findElements(mediaTitlesBy).get(index);
    targetMedia.click();
    waitForDialogNotVisible();
    return new VisualEditorPageObject(driver);
  }

  private void selectFileToUpload(String fileName) {
    fileUploadInput.sendKeys(
        getAbsolutePathForFile(PageContent.RESOURCES_PATH + fileName)
    );
    PageObjectLogging.log(
        "selectFileToUpload",
        "file " + fileName + " added to upload",
        true
    );
  }

  public VisualEditorPageObject previewExistingMediaByTitle(String title) {
    waitForDialogVisible();
    WebElement media = findMediaByTitle(title);
    media.click();
    PageObjectLogging.log("previewExistingMediaByTitle", "Media clicked", true);
    return new VisualEditorPageObject(driver);
  }

  private WebElement findMediaByTitle(String title) {
    WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
    waitForElementVisibleByElement(mediaResultsWidget);
    return getElementByValue(mediaResultsWidget.findElements(mediaTitlesBy), "title", title);
  }
}
