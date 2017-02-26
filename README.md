# Ocelot Wars

This is a host for Ocelot Wars programming competitions.
In the competition, programmers create bots that participate in Ocelot Wars games via a language agnostic REST-based API.

An Ocelot Wars game consists of a two dimensional map containing resources, player units and player bases.
The objective of the game is to gather as many resources as possible.
When the map's resources are depleted, the player with the most gathered resources wins.

A spectator client visualizes the score, currently running games and replays of past games.
This allows the audience and the programming participants to get a feeling for the progress of the competition.

The general idea of the programming competition is based on [Mäxchen](https://github.com/conradthukral/maexchen).

## Vision

Later iterations of the game will potentially

- differentiate the REST API into
    - a simple REST API for quick programming competitions (multi-hour)
    - a more advanced REST API for fine-grained control and longer competitions (multi-day)
- potentially play in space or on earth
- have a different game goal, e.g. player that survives wins (implies some way of eliminating the other players)
- have various unit types
- have units that need resources to be constructed
- have resource types
- have technology tree(s)

## Building ocelot-wars

[![Build Status](https://api.travis-ci.org/ocelot-wars/ocelot-wars.svg)](https://travis-ci.org/ocelot-wars/ocelot-wars)
[![codecov](https://codecov.io/gh/ocelot-wars/ocelot-wars/branch/master/graph/badge.svg)](https://codecov.io/gh/ocelot-wars/ocelot-wars)



We use Gradle as the build system with the Gradle Wrapper. To build the project either use
```
./gradlew
```
on Linux/Mac or
```
gradlew.bat
```
on Windows appending the desired command like `test` to the call.

## Formatting settings

We use [EditorConfig](http://editorconfig.org/) for the basic formatting settings. There are [plugins available](http://editorconfig.org/#download) for most IDEs and editors.
