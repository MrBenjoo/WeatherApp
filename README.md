# SwedeCast

[![Build Status](https://travis-ci.org/mrbenjoo/WeatherApp.svg?branch=master)](https://travis-ci.org/mrbenjoo/WeatherApp)

## What is SwedeCast?
SwedeCast is a Kotlin based weather application displaying weather forecast for a given location in Sweden. The app uses coroutines for concurrency and multi-module communication to avoid ambiguity and achieve clear separation of concerns to make the project easier to work with. Additonally, the following components from the Android Architecture library are utilised: navigation, viewModel, liveData and constraintLayout.

## APIs and Architectures Used
- Android Jetpack/Components: Navigation, ViewModel, LiveData, ConstraintLayout
- [Weather data is retrieved from SMHI's API](http://opendata.smhi.se/apidocs/metfcst/index.html)
- [Geocoding Service](https://developers.arcgis.com/rest/geocode/api-reference/overview-world-geocoding-service.htm)
- Follows the MVVM design pattern