<h1 align="center">SwedeCast</h1>

<p align="center">
<a href="https://travis-ci.org/mrbenjoo/WeatherApp"><img src="https://travis-ci.org/mrbenjoo/WeatherApp.svg?branch=master" alt="Build Status"></a>
<a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center"> 
SwedeCast is a Kotlin based weather application displaying weather forecast for a given location in Sweden.<br>
The app uses coroutines for concurrency and multi-module communication to avoid ambiguity and achieve<br>
clear separation of concerns to make the project easier to work with. Additonally, the following components <br>
from the Android Architecture library are utilised: navigation, viewModel, liveData and constraintLayout. 
</p>
</br>

<table style="text-align: center">
  <tr>
    <td align="center">Daily</td>
     <td align="center">Hourly</td>
  </tr>
  <tr>
    <td><img src="/previews/1.png"></td>
    <td><img src="/previews/2.png"></td>
  </tr>
 </table>
 
 <table>
  <tr>
    <td align="center">Currently</td>
    <td align="center">Search</td>
  </tr>
  <tr>
    <td><img src="/previews/3.png"></td>
    <td><img src="/previews/4.png"></td>
  </tr>
 </table>

<img src="/previews/preview.gif" align="right" width="32%"/>

## APIs and Architectures Used
- Android Jetpack/Components: Navigation, ViewModel, LiveData, ConstraintLayout
- [Weather data is retrieved from SMHI's API](http://opendata.smhi.se/apidocs/metfcst/index.html)
- [Geocoding Service](https://developers.arcgis.com/rest/geocode/api-reference/overview-world-geocoding-service.htm)
- Follows the MVVM design pattern


