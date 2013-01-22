# Selenium Tests
This repository contains [Selenium](http://seleniumhq.org/) tests for the [Wikia App](https://github.com/Wikia/app).

## Adding Tests
Please remember to keep this repository up to date as the tests become worthless otherwise. A good rule of thumb is to add a new test that covers whatever bug you're fixing so we can track regressions on it. Also, make sure to code your tests in a cross-operating system compliant manor as it's valid to run them in Windows, Mac or Linux environments.

## Running Tests

The following steps should get you set up for running Selenium tests locally on your machine:

1. First, make sure you have [Java](http://www.java.com/) and [Maven](http://maven.apache.org/) installed on your system, if not follow the vendor instructions for installing them on your operating system.
2. Clone this repository to your local machine (not your devbox).
3. Open up a terminal and navigate to the root directory of the repository.

Using Maven, you may now run any tests you want on whatever database and domain you want:

    mvn clean test -Dbrowser=FF -Dbase-address=<address> -Dgroups=<group> -Dlive-domain=<address>

### Parameters

The following are valid test parameters:

* `-Dbrowser` - Which browser to use, for example "CHROME"
* `-Dbase-address` - The base URL to pull the database from, for example "http://yourname.wikia-dev.com/"
* `-Dgroups` - Which test groups to run, for example "Chat"
* `-Dlive-domain` - The base URL to run in the browser, for example "http://yourname.wikia-dev.com/"

### Browsers

The following are valid for use in the `-Dbrowser` parameter:

* FF
* CHROME
* IE

### Groups

The following are valid for use in the `-Dgroups` parameter:

* ArticleCRUDAdmin
* ArticleCRUDAnonymous
* ArticleFeaturesCRUDAdmin
* CategoriesTestsAnonymous
* Chat
* CNW_lang
* CNW
* Hubs
* ImageServing
* Login
* MessageWall
* RTE
* Toolbar

## Reading Logs

Most tests write logs and take screenshots while they run which provide further information about what happened if they failed. These files reside in the `./logs` directory and can be viewed locally in your browser by navigating to that folder.