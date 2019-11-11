package com.benji.data.datasource.local

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly

object Constants {

    val HOURLY_JSON = "{\n" +
            "  \"validTime\": \"2019-10-06T12:00:00Z\",\n" +
            "  \"parameters\": [\n" +
            "    {\n" +
            "      \"name\": \"msl\",\n" +
            "      \"levelType\": \"hmsl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"hPa\",\n" +
            "      \"values\": [\n" +
            "        990.5\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"t\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 2,\n" +
            "      \"unit\": \"Cel\",\n" +
            "      \"values\": [\n" +
            "        11.8\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"vis\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 2,\n" +
            "      \"unit\": \"km\",\n" +
            "      \"values\": [\n" +
            "        49.5\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"wd\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 10,\n" +
            "      \"unit\": \"degree\",\n" +
            "      \"values\": [\n" +
            "        275\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"ws\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 10,\n" +
            "      \"unit\": \"m/s\",\n" +
            "      \"values\": [\n" +
            "        12.7\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"r\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 2,\n" +
            "      \"unit\": \"percent\",\n" +
            "      \"values\": [\n" +
            "        70\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"tstm\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"percent\",\n" +
            "      \"values\": [\n" +
            "        4\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"tcc_mean\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"octas\",\n" +
            "      \"values\": [\n" +
            "        8\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"lcc_mean\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"octas\",\n" +
            "      \"values\": [\n" +
            "        2\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"mcc_mean\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"octas\",\n" +
            "      \"values\": [\n" +
            "        8\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"hcc_mean\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"octas\",\n" +
            "      \"values\": [\n" +
            "        8\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"gust\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 10,\n" +
            "      \"unit\": \"m/s\",\n" +
            "      \"values\": [\n" +
            "        26.8\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"pmin\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"kg/m2/h\",\n" +
            "      \"values\": [\n" +
            "        0.9\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"pmax\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"kg/m2/h\",\n" +
            "      \"values\": [\n" +
            "        1.3\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"spp\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"percent\",\n" +
            "      \"values\": [\n" +
            "        0\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"pcat\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"category\",\n" +
            "      \"values\": [\n" +
            "        3\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"pmean\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"kg/m2/h\",\n" +
            "      \"values\": [\n" +
            "        1\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"pmedian\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"kg/m2/h\",\n" +
            "      \"values\": [\n" +
            "        1\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Wsymb2\",\n" +
            "      \"levelType\": \"hl\",\n" +
            "      \"level\": 0,\n" +
            "      \"unit\": \"category\",\n" +
            "      \"values\": [\n" +
            "        20\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}"


}