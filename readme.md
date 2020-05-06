# Installation & Configuration

In order to start using the FocusPoints client functionality we need to choose the right module for our project and provide it with the correct configuration.

## Manual Configuration

Add the following dependency to your project

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-core</artifactId>
	<version>1.0.1</version>
</dependency>
```

During application startup the following classes need to be configured: `ImageServiceConfigurationProperties`, `TokenCreator` and `UrlCreator`. Below is an example of such a configuration:

```java
ImageServiceConfigurationProperties imageServiceConfiguration = new ImageServiceConfigurationProperties();
imageServiceConfiguration.setTokenId("myTokenId");
imageServiceConfiguration.setTokenSecret("myTokenSecret");

new TokenCreator(imageServiceConfiguration);
new UrlCreator(imageServiceConfiguration);
```

The `TokenCreator` and `UrlCreator` are singleton classes but need to be initialized once with the correct configuration. It is not needed to keep a reference to them.

## ServletContext Configuration

Add the following dependency to your project

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-core-servlet</artifactId>
	<version>1.0.1</version>
</dependency>
```

This will setup a WebListener at application startup that initializes the FocusPoints client. Make sure the following context params are present in your application

| Name                     | Description                              |
|--------------------------|------------------------------------------|
| focuspoints.token-id     | Your FocusPoints API Key ID              |
| focuspoints.token-secret | Your FocusPoints API Key Secret          |


## Spring Configuration
Add the following dependency to your project

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-core-spring</artifactId>
	<version>1.0.1</version>
</dependency>
```

Add the following import to your Spring configuration

```java
@Import(FocusPointsConfiguration.class)
```

Make sure the following properties are present in your environment configuration

| Name                     | Description                              |
|--------------------------|------------------------------------------|
| focuspoints.token-id     | Your FocusPoints API Key ID              |
| focuspoints.token-secret | Your FocusPoints API Key Secret          |


## Spring Boot Configuration
Add the following dependency to your projetc

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-core-spring-boot-starter</artifactId>
	<version>1.0.1</version>
</dependency>
```

This will cause the `io.focuspoints.client.FocusPointsAutoConfiguration` to be automatically included at application startup.


Make sure the following properties are present in your environment configuration

| Name                     | Description                              |
|--------------------------|------------------------------------------|
| focuspoints.token-id     | Your FocusPoints API Key ID              |
| focuspoints.token-secret | Your FocusPoints API Key Secret          |

## Configuration Properties

The following configuration properties are supported.

| Name                                     | Required | Default Value                | Description                                                                        |
|------------------------------------------|----------|------------------------------|------------------------------------------------------------------------------------|
| focuspoints.token-id                     | True     |                              | Your FocusPoints API Key ID                                                        |
| focuspoints.token-secret                 | True     |                              | Your FocusPoints API Key Secret                                                    |
| focuspoints.enabled                      | False    | True                         | Enables the use of focuspoints. When disabled, the original image URL will be used |
| focuspoints.url                          | False    | https://image.focuspoints.io | The focuspoints server endpoint                                                    |
| focuspoints.token-request-parameter-name | False    | _jwt                         | The name of the request parameter containing the token                             |

# Using FocusPoints in your frontend

## JSP and Freemarker

Add the following dependency to your projetc

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-taglib</artifactId>
	<version>1.0.1</version>
</dependency>
```

This will add a taglib to your project containing a tld with uri `http://www.focuspoints.io/tags`. The tld defines two tags used for image transformation and image resizing.

### The image transformation tag

The image transformation tag generates a URL containing the correct parameters for transforming an image using the desired parameters. The images created by the transformation operation always matches the desired with and height. When it is required to crop certain parts of the image the focus point coordinate is used to determin which part of the image must not be removed.

| Attribute   | Required | Description |
|-------------|----------|-------------|
| url         | true     | An absolute URL of the image that is used. |
| filename    | false    | The filename of the image. |
| width       | true     | The desired width of the generated image variant in pixels. Must be a positive `Integer` value |
| height      | true     | The desired height of the generated image variant in pixels. Must be a positive `Integer` value |
| focusPointX | false    | The focus point coordinate for the x-axis. Must be a `Double` value between -1 and 1. |
| focusPointY | false    | The focus point coordinate for the y-axis. Must be a `Double` value between -1 and 1. |
| var         | false    | The string to use when binding the result to the page, request, session or application scope. If not specified the result gets outputted to the writer (i.e. typically directly to the JSP) |
| scope       | false    | The scope to use when exporting the result to a variable. This attribute is only used when **var** is also set. Possible values are **page**, **request**, **session** and **application**. When no value is specified this defaults to the **page** scope |

#### Examples
```xml
<%-- Import imageservice taglib --%>
<%@ taglib prefix="focuspoints" uri="http://www.focuspoints.io/tags" %>

<%-- Transform an image using the settings as specified in the tag --%>
<focuspoints:transform url="https://path/to/my/image" filename="my-image.jpg" width="1280" height="360" focusPointX="0.6" focusPointY="-0.35" />

<%-- Transform an image using the settings as specified in the tag and store the result in the page scope --%>
<focuspoints:transform var="imageTransformed1280x360" url="https://path/to/my/image" filename="my-image.jpg" width="1280" height="360" focusPointX="0.6" focusPointY="-0.35" />
```

### The image resize tag

The image resize tag generates a URL containing the correct parameters for resizing an image using the desired parameters. Where the image transformation tag can crop certain parts of an image this is not always desired (e.g. when displaying a company logo). The resize operation makes sure the image fits within the desired dimensions but only fits the longest side of the image. The shortest side could therefor be smaller than the provided with or height.

| Attribute | Required | Description |
|-----------|----------|-------------|
| url       | true     | An absolute URL of the image that is used. |
| filename  | false    | The filename of the image. |
| width     | true     | The desired width of the generated image variant in pixels. Must be a positive `Integer` value |
| height    | true     | The desired height of the generated image variant in pixels. Must be a positive `Integer` value |
| var       | false    | The string to use when binding the result to the page, request, session or application scope. If not specified the result gets outputted to the writer (i.e. typically directly to the JSP) |
| scope     | false    | The scope to use when exporting the result to a variable. This attribute is only used when **var** is also set. Possible values are **page**, **request**, **session** and **application**. When no value is specified this defaults to the **page** scope |

#### Examples
```xml
<%-- Import imageservice taglib --%>
<%@ taglib prefix="focuspoints" uri="http://www.focuspoints.io/tags" %>

<%-- Resize an image to the desired with and height --%>
<focuspoints:resize url="https://path/to/my/image" filename="my-image.jpg" width="1280" height="360" />

<%-- Resize an image to the desired with and height --%>
<focuspoints:resize var="imageResized1280x360" url="https://path/to/my/image" filename="my-image.jpg" width="1280" height="360" />
```

## Thymeleaf

Add the following dependency to your projetc

```xml
<dependency>
	<groupId>io.focuspoints</groupId>
	<artifactId>focuspoints-client-thymeleaf</artifactId>
	<version>1.0.1</version>
</dependency>
```

This will provide a io.focuspoints.client.thymeleaf.FocusPointsDialect that needs to be configured with the Thymeleaf Engine

```java
TemplateEngine templateEngine = new TemplateEngine();
templateEngine.addDialect(new FocusPointsDialect());
```

For those using Spring Boot you can simply define it as a Bean in you applciation context

```java
@Bean
public LayoutDialect focusPointsDialect() {
    return new FocusPointsDialect();
}
```

This will add the `#focuspoints` object with the following functions

```java
${#focuspoints.transform(url, filename, width, height)}
${#focuspoints.transform(url, filename, width, height, focusPointX, focusPointY)}

${#focuspoints.resize(url, filename, width, height)}
```

### The transformation operation

The transformation operation generates a URL containing the correct parameters for transforming an image using the desired parameters. The images created by the transformation operation always matches the desired with and height. When it is required to crop certain parts of the image the focus point coordinate is used to determin which part of the image must not be removed.

| Parameter   | Description |
|-------------|-------------|
| url         | An absolute URL of the image that is used. |
| filename    | The filename of the image. |
| width       | The desired width of the generated image variant in pixels. Must be a positive `Integer` value |
| height      | The desired height of the generated image variant in pixels. Must be a positive `Integer` value |
| focusPointX | The focus point coordinate for the x-axis. Must be a `Double` value between -1 and 1. |
| focusPointY | The focus point coordinate for the y-axis. Must be a `Double` value between -1 and 1. |

### The resize operation

The resize operation generates a URL containing the correct parameters for resizing an image using the desired parameters. Where the image transformation tag can crop certain parts of an image this is not always desired (e.g. when displaying a company logo). The resize operation makes sure the image fits within the desired dimensions but only fits the longest side of the image. The shortest side could therefor be smaller than the provided with or height.

| Attribute | Description |
|-----------|-------------|
| url       | An absolute URL of the image that is used. |
| filename  | The filename of the image. |
| width     | The desired width of the generated image variant in pixels. Must be a positive `Integer` value |
| height    | The desired height of the generated image variant in pixels. Must be a positive `Integer` value |

# Serving images from your own domain
When using FocusPoints the domain your images are services from will be `images.focuspoints.io`. If this is undesirable behavior it is possible to serve the images from your own domain by using a proxy. In order to so use the following steps:
1. Set the FocusPoints URL in the configuration to a relative path (e.g. `/images/`). Either do so by setting it on `ImageServiceConfigurationProperties` or through the configuration property `focuspoints.url` depending on which configuration you use.
2. Create a proxy pass in order to proxy all requests for the `/images/` path to `https://images.focuspoints.io`. An example using Apache HTTPD can be found below

```xml
<Location "/images/">
	ProxyPass "https://images.focuspoints.io"
</Location>
```