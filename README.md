# gwt-oauth

OAuth library for GWT

## Original Work by Matt Raible
At this stage, largely a clone of [Matt Raible's](https://twitter.com/mraible) in this area [here](https://raibledesigns.com/rd/entry/implementing_oauth_with_gwt) and [here](https://raibledesigns.com/rd/entry/gwt_oauth_and_linkedin_apis). Although I couldn't find an active repository from which to actually fork the original work. Matt's work was a long time back, and so there are (understandably) a few broken links when trying to use it. The main ones being the required javascript sources. These were located with [this SO topic](https://stackoverflow.com/questions/37183524/http-oauth-googlecode-com-svn-code-javascript-oauth-js-is-down-please-provide), and have been downloaded and included directly in the GWT `public/js` folder to avoid future breakages. Matt's most recent release was dubbed "1.3" in the second of his blog entries, and hence we begin with a version of 1.4 in this repository.

## Background
So why resurrect an 11 year old exercise? Well, as in all things, necessity. Some of [Ascert's](https://ascert.com) product lines are built around a Java server app that supports OAuth 1.0 security on it's REST API. As part of migrating our GWT client code from GwtRpc to RestyGWT, we needed to able to integrate with the current OAuth security layer. Hence, future maintenance that we conduct on this library is likely to expand out in that direction i.e. better and richer support for OAuth 1.0, RestyGWT and Jersey/JAX-RS. It's unlikely we will take the work Matt started on integrations with the likes of Google, LinkedIn etc. further, but feel free to contribute patches if you happen to do so.

## What's this good for?
Although still included, Matt's examples aren't really the objective here (they need some massaging to resurrect them). The main goal of this package is to support generation of OAuth 1.0 headers suitable for use with a [RestyGWT](https://github.com/resty-gwt/resty-gwt) based app. Although out of vogue these days, there's still a fair number of GWT apps out there in production. Extending those to support REST and OAuth is a way to bring that code up to a more open and modern standard, and ease the migration to other toolsets by allowing selective replacement of some panels with other frameworks where appropriate.

## Build
Reluctantly, after banging my head against it for longger than I should, I've had to accept a build approach using 2 different Gradle GWT plugins, split across 3 different build files. GWT is somewhat out of vogue, and my Gradle skills aren't quite up to making 1 plugin & build file do every job. I'm sure someone smarter than me or with more time can figure a better approach! Who knows, if COVID-19 lockdowns carry on maybe I'll even find time.

### Creating the JAR package
Should be easy right? Sadly not. The best plugin I could find for this part, barfs on the inclusion of GWT tests. So rather than the usual build target, use of the following (which I have a feelign are equivalent under the hood anyway):

```
gradle assemble
gradle build -x test
```

The GWT packaged JAR file can then be found in `build/libs`

### Running the examples
OK, the backend's don't work, but it's nice to run something right? Sadly, the ability to run a war target needs a different variation of the plugin for building. I'm sure there's a tidy way to have both targets in the same build file using sub-projects, but I'd already lost too much time already and keeping them separate was easier!

```
gradle -b example.gradle clean gwtRun
```

I'd recommend the clean in case you have any pollution left from using the other build files/targets

While we're at it, you can also launch SuperDev mode here too. It worked briefly for me, but I changed something which I cannot figure and now the Dev Mode On -> Compile button doesn't work. Anyhow, to try it type in a separate console:

```
gradle -b example.gradle gwtCodeServer
```

### Testing
Sadly, the plugin for the previous 2 steps has no knowledge of GWT Test. I did find an alternate which does seem to work though, which again I split out into an alternate build file.

```
gradle -b test.gradle clean test
```

The only tests currently present are for SHA1 and OAuth 1.0 signature and header verification i.e the current main goal of the library.

_Note: The plugin used for testing can also run the app successfully. Again though, SuperDev mode breaks albeit in a different way. The dev_mode.js resource cannot be located - or in fact any resources!_

## Matt's original example
Still included, and the index.html & servlets load. Back end plumbing isn't fully working. For info, Matt's original README.TXT and pom.xml can be found in the initial commit.


