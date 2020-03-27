# gwt-oauth

OAuth library for GWT

## Original Work by Matt Raible
At this stage, largely a clone of [Matt Raible's](https://twitter.com/mraible) in this area [here](https://raibledesigns.com/rd/entry/implementing_oauth_with_gwt) and [here](https://raibledesigns.com/rd/entry/gwt_oauth_and_linkedin_apis). Although I couldn't find an active repository from which to actually fork the original work. Matt's work was a long time back, and so there are (understandably) a few broken links when trying to use it. The main ones being the required javascript sources. These were located with [this SO topic](https://stackoverflow.com/questions/37183524/http-oauth-googlecode-com-svn-code-javascript-oauth-js-is-down-please-provide), and have been downloaded and included directly in the GWT `public/js` folder to avoid future breakages. Matt's most recent release was dubbed "1.3" in the second of his blog entries, and hence we begin with a version of 1.4 in this repository.

## Background
So why resurrect an 11 year old exercise? Well, as in all things, necessity. Some of [Ascert's](https://ascert.com) product lines are built around a Java server app that supports OAuth 1.0 security on it's REST API. As part of migrating our GWT client code from GwtRpc to RestyGWT, we needed to able to integrate with the current OAuth security layer. Hence, future maintenance that we conduct on this library is likely to expand out in that direction i.e. better and richer support for OAuth 1.0, RestyGWT and Jersey/JAX-RS. It's unlikely we will take the work Matt started on integrations with the likes of Google, LinkedIn etc. further, but feel free to contribute patches if you happen to do so.

## Package build

Transitioning to gradle means most of the lifting is now done for us. Build as follows:

```
gradle build
```

GWT packaged JAR file can then be found in `build/libs`

## Matt's original example

Still included, and the index.html & servlets load. Back end plumbing doesn't seem to be fully working. With the transition to Gradle, the run command is now:

```
gradle -b example.gradle clean gwtRun
```

For info, Matt's original README.TXT and pom.xml can be found in the initial commit.


