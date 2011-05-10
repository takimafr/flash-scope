# flash-scope 1.0.0

## What for?

Simple Flash scope for Redirect-After-Post pattern.

* Declare the FlashScopeFilter in your web.xml and trap your pages
* Use FlashScope.bind("attributeName").value(attributeValue);

For further details, please have a look at the javadoc.

## Where are the binaries?

You can find the binaries in our maven repository :
[http://repository.excilys.com/content/repositories/releases](http://repository.excilys.com/content/repositories/releases)

``` xml
	<dependency>
		<groupId>com.excilys.utils</groupId>
		<artifactId>flash-scope</artifactId>
		<version>1.0.0</version>
	</dependency>
```