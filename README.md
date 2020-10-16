# WKND-MUZIK - Adobe Lean Code Contest

This project showcases an AEM Implementation that leverages the Digital Foundation Blueprint best practices and 
implementation patterns. It's implemented by [Initialyze Inc](https://www.initialyze.com) and submitted for 
consideration in the [Digital Foundation Blueprint: Lean Code Contest](https://solutionpartners.adobe.com/home/news/2020/09/digital_foundation_b.html)

The following [implementation design and reference instructions](https://github.com/initialyze/wknd-muzik/blob/master/Implementation-Design-Reference.pdf) provide more details about this implementation. 

**TESTED WITH**
* Adobe AEM 6.5 (Service Pack 6)
* WCM Core Components 2.11.0
* Chrome (83+)
* Browser Width: 1280px

**URLS**
* Author: [http://localhost:4502/content/wknd-muzik/language-masters/en/home-page.html](http://localhost:4502/content/wknd-muzik/language-masters/en/home-page.html)
* Publish:
  1. Homepage - [http://localhost:4503/content/wknd-muzik/us/en/home-page.html](http://localhost:4503/content/wknd-muzik/us/en/home-page.html)
  2. Learn More - [http://localhost:4503/content/wknd-muzik/us/en/home-page/learn-more.html](http://localhost:4503/content/wknd-muzik/us/en/home-page/learn-more.html)
  3. Sign Up  - [http://localhost:4503/content/wknd-muzik/us/en/home-page/memberships/sign-up.html](http://localhost:4503/content/wknd-muzik/us/en/home-page/memberships/sign-up.html)
  3. Confirmation - [http://localhost:4503/content/wknd-muzik/us/en/home-page/memberships/sign-up/confirmation.html](http://localhost:4503/content/wknd-muzik/us/en/home-page/memberships/sign-up/confirmation.html)


## Features
 
* Leverages [WCM Core Components 2.11.0](https://github.com/adobe/aem-core-wcm-components)
* Minimal Customization of core components performed by using Extension best practices (https://docs.adobe.com/content/help/en/experience-manager-core-components/using/developing/customizing.html).
* Component Styling configurable using Style System and policies supporting various display attributes such as variations, spacings, background colors
* All components leverage BEM notation for CSS classes in the markup


## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* it.tests: Java based integration tests
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)
* ui.tests: Selenium based UI tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallSinglePackagePublish

Or alternatively

    mvn clean install -PautoInstallSinglePackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only a single content package, run in the sub-module directory (i.e `ui.apps`)

    mvn clean install -PautoInstallPackage

## Testing

There are three levels of testing contained in the project:

### Unit tests

This show-cases classic unit testing of the code contained in the bundle. To
test, execute:

    mvn clean test

### Integration tests

This allows running integration tests that exercise the capabilities of AEM via
HTTP calls to its API. To run the integration tests, run:

    mvn clean verify -Plocal

Test classes must be saved in the `src/main/java` directory (or any of its
subdirectories), and must be contained in files matching the pattern `*IT.java`.

The configuration provides sensible defaults for a typical local installation of
AEM. If you want to point the integration tests to different AEM author and
publish instances, you can use the following system properties via Maven's `-D`
flag.

| Property | Description | Default value |
| --- | --- | --- |
| `it.author.url` | URL of the author instance | `http://localhost:4502` |
| `it.author.user` | Admin user for the author instance | `admin` |
| `it.author.password` | Password of the admin user for the author instance | `admin` |
| `it.publish.url` | URL of the publish instance | `http://localhost:4503` |
| `it.publish.user` | Admin user for the publish instance | `admin` |
| `it.publish.password` | Password of the admin user for the publish instance | `admin` |

The integration tests in this archetype use the [AEM Testing
Clients](https://github.com/adobe/aem-testing-clients) and showcase some
recommended [best
practices](https://github.com/adobe/aem-testing-clients/wiki/Best-practices) to
be put in use when writing integration tests for AEM.

### UI tests

They will test the UI layer of your AEM application using Selenium technology. 

To run them locally:

    mvn clean verify -Pui-tests-local-execution

This default command requires:
* an AEM author instance available at http://localhost:4502 (with the whole project built and deployed on it, see `How to build` section above)
* Chrome browser installed at default location

Check README file in `ui.tests` module for more details.

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.
