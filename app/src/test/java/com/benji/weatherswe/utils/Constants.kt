package com.benji.weatherswe.utils

object Constants {

     /*
     const val JSON_WEATHER_TEST_DATA_20_TO_23 = "{\n" +
            "  \"date\": \"2019-09-30\",\n" +
            "  \"day\": \"Måndag\",\n" +
            "  \"city\": \"Malmö\",\n" +
            "  \"temperature\": \"18\",\n" +
            "  \"listOfHourlyData\": [\n" +
            "    {\n" +
            "      \"validTime\": \"2019-11-20T20:00:00Z\",\n" +
            "      \"parameters\": [\n" +
            "        {\n" +
            "          \"name\": \"msl\",\n" +
            "          \"levelType\": \"hmsl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"hPa\",\n" +
            "          \"values\": [\n" +
            "            1018.2\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"t\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"Cel\",\n" +
            "          \"values\": [\n" +
            "            7.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"vis\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"km\",\n" +
            "          \"values\": [\n" +
            "            2.7\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"wd\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"degree\",\n" +
            "          \"values\": [\n" +
            "            107\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"ws\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            5.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"r\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            96\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tstm\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"lcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"mcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"hcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"gust\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            9.3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmin\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmax\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"spp\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            -9\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pcat\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmedian\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Wsymb2\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            6\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"temp\": \"18c\",\n" +
            "      \"weatherSymbol\": \"2552250\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"validTime\": \"2019-11-20T21:00:00Z\",\n" +
            "      \"parameters\": [\n" +
            "        {\n" +
            "          \"name\": \"msl\",\n" +
            "          \"levelType\": \"hmsl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"hPa\",\n" +
            "          \"values\": [\n" +
            "            1018.2\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"t\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"Cel\",\n" +
            "          \"values\": [\n" +
            "            7.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"vis\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"km\",\n" +
            "          \"values\": [\n" +
            "            2.7\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"wd\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"degree\",\n" +
            "          \"values\": [\n" +
            "            107\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"ws\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            5.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"r\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            96\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tstm\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"lcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"mcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"hcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"gust\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            9.3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmin\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmax\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"spp\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            -9\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pcat\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmedian\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Wsymb2\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            6\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"temp\": \"18c\",\n" +
            "      \"weatherSymbol\": \"2552250\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"validTime\": \"2019-11-20T22:00:00Z\",\n" +
            "      \"parameters\": [\n" +
            "        {\n" +
            "          \"name\": \"msl\",\n" +
            "          \"levelType\": \"hmsl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"hPa\",\n" +
            "          \"values\": [\n" +
            "            1018.2\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"t\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"Cel\",\n" +
            "          \"values\": [\n" +
            "            7.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"vis\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"km\",\n" +
            "          \"values\": [\n" +
            "            2.7\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"wd\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"degree\",\n" +
            "          \"values\": [\n" +
            "            107\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"ws\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            5.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"r\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            96\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tstm\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"lcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"mcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"hcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"gust\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            9.3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmin\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmax\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"spp\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            -9\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pcat\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmedian\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Wsymb2\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            6\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"temp\": \"18c\",\n" +
            "      \"weatherSymbol\": \"2552250\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"validTime\": \"2019-11-20T23:00:00Z\",\n" +
            "      \"parameters\": [\n" +
            "        {\n" +
            "          \"name\": \"msl\",\n" +
            "          \"levelType\": \"hmsl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"hPa\",\n" +
            "          \"values\": [\n" +
            "            1018.2\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"t\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"Cel\",\n" +
            "          \"values\": [\n" +
            "            7.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"vis\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"km\",\n" +
            "          \"values\": [\n" +
            "            2.7\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"wd\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"degree\",\n" +
            "          \"values\": [\n" +
            "            107\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"ws\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            5.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"r\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            96\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tstm\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"lcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"mcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"hcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"gust\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            9.3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmin\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmax\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"spp\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            -9\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pcat\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmedian\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Wsymb2\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            6\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"temp\": \"18c\",\n" +
            "      \"weatherSymbol\": \"2552250\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"weatherSymbol\": \"255200\"\n" +
            "}"

    const val JSON_WEATHER_TEST_DATA_00 = "{\n" +
            "  \"date\": \"2019-09-30\",\n" +
            "  \"day\": \"Måndag\",\n" +
            "  \"city\": \"Malmö\",\n" +
            "  \"temperature\": \"18\",\n" +
            "  \"listOfHourlyData\": [\n" +
            "    {\n" +
            "      \"validTime\": \"2019-11-21T00:00:00Z\",\n" +
            "      \"parameters\": [\n" +
            "        {\n" +
            "          \"name\": \"msl\",\n" +
            "          \"levelType\": \"hmsl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"hPa\",\n" +
            "          \"values\": [\n" +
            "            1018.2\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"t\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"Cel\",\n" +
            "          \"values\": [\n" +
            "            7.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"vis\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"km\",\n" +
            "          \"values\": [\n" +
            "            2.7\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"wd\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"degree\",\n" +
            "          \"values\": [\n" +
            "            107\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"ws\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            5.5\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"r\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 2,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            96\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tstm\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"tcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"lcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            8\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"mcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"hcc_mean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"octas\",\n" +
            "          \"values\": [\n" +
            "            1\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"gust\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 10,\n" +
            "          \"unit\": \"m/s\",\n" +
            "          \"values\": [\n" +
            "            9.3\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmin\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmax\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"spp\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"percent\",\n" +
            "          \"values\": [\n" +
            "            -9\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pcat\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmean\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"pmedian\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"kg/m2/h\",\n" +
            "          \"values\": [\n" +
            "            0\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Wsymb2\",\n" +
            "          \"levelType\": \"hl\",\n" +
            "          \"level\": 0,\n" +
            "          \"unit\": \"category\",\n" +
            "          \"values\": [\n" +
            "            6\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"temp\": \"18c\",\n" +
            "      \"weatherSymbol\": \"2552250\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"weatherSymbol\": \"255200\"\n" +
            "}"

      */
}