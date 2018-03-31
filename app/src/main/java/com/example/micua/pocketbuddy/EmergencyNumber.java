package com.example.micua.pocketbuddy;

import java.util.HashMap;
import java.util.Map;

public class EmergencyNumber {
    private String number;
    private Map<String, String> numbers;

    public EmergencyNumber() {
        this.numbers = new HashMap<>();
        init();
    }
    //TODO: add more phone numbers for other emergency services
    private void init() {
        numbers.put("Algeria" +
                "", "police:18,fire:14,ambulance:14");
        numbers.put("Angola" +
                "", "police:69,fire:69,ambulance:69");
        numbers.put("Ascension Island" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Benin" +
                "", "police:117,fire:112,ambulance:118");
        numbers.put("Burundi" +
                "", "police:117,fire:112,ambulance:118");
        numbers.put("Botswana" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Burkina Faso" +
                "", "police:17,fire:112,ambulance:18");
        numbers.put("Cameroon" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Cape Verde" +
                "", "police:132,fire:130,ambulance:131");
        numbers.put("Central African Republic" +
                "", "police:117,fire:1220,ambulance:118");
        numbers.put("Chad" +
                "", "police:17,fire:2251-4242,ambulance:18");
        numbers.put("Comoros" +
                "", "police:17,fire:772-03-73,ambulance:18");
        numbers.put("Republic of Congo" +
                "", "police:117,fire:none,ambulance:118");
        numbers.put("Democratic Republic of Congo" +
                "", "police:112,fire:none,ambulance:118");
        numbers.put("Djibouti" +
                "", "police:17,fire:19,ambulance:18");
        numbers.put("Egypt" +
                "", "police:122,fire:180,ambulance:123");
        numbers.put("Equatorial Guinea" +
                "", "police:114,fire:115,ambulance:112");
        numbers.put("Eritrea" +
                "", "police:113,fire:114,ambulance:116");
        numbers.put("Ethiopia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Gabon" +
                "", "police:1730,fire:1300,ambulance:18");
        numbers.put("Gambia" +
                "", "police:117,fire:116,ambulance:118");
        numbers.put("Ghana" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Guinea" +
                "", "police:117,fire:18,ambulance:442-020");
        numbers.put("Guinea-Bissau" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Ivory Coast" +
                "", "police:111,fire:185,ambulance:180");
        numbers.put("Liberia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Kenya" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Libya" +
                "", "police:1515,fire:1515,ambulance:1515");
        numbers.put("Lesotho" +
                "", "police:123,fire:121,ambulance:122");
        numbers.put("Madagascar" +
                "", "police:117,fire:124,ambulance:118");
        numbers.put("Malawi" +
                "", "police:997,fire:998,ambulance:999");
        numbers.put("Mali" +
                "", "police:17,fire:15,ambulance:18");
        numbers.put("Mauritius" +
                "", "police:112,fire:114,ambulance:115");
        numbers.put("Mauritania" +
                "", "police:117,fire:101,ambulance:118");
        numbers.put("Mayotte" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Morocco" +
                "", "police:19,fire:15,ambulance:15");
        numbers.put("Mozambique" +
                "", "police:119,fire:117,ambulance:198");
        numbers.put("Namibia" +
                "", "police:10111,fire:none,ambulance:none");
        numbers.put("Nigeria" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Niger" +
                "", "police:17,fire:15,ambulance:18");
        numbers.put("Reunion" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Rwanda" +
                "", "police:112,fire:912,ambulance:112");
        numbers.put("Saint Helena" +
                "", "police:999,fire:911,ambulance:999");
        numbers.put("Sao Tome and Principe" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Senegal" +
                "", "police:17,fire:18,ambulance:1515");
        numbers.put("Seychelles" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Sierra Leone" +
                "", "police:019,fire:999,ambulance:999");
        numbers.put("Somalia" +
                "", "police:888,fire:999,ambulance:555");
        numbers.put("South Africa" +
                "", "police:10 111,fire:10 177,ambulance:10 177");
        numbers.put("Sudan" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("South Sudan" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Swaziland" +
                "", "police:999,fire:977,ambulance:933");
        numbers.put("Tanzania" +
                "", "police:112,fire:114,ambulance:115");
        numbers.put("Togo" +
                "", "police:117,fire:8200,ambulance:118");
        numbers.put("Tristan da Cunha" +
                "", "police:999,fire:911,ambulance:999");
        numbers.put("Tunisia" +
                "", "police:197,fire:198,ambulance:190");
        numbers.put("Uganda" +
                "", "police:112,fire:911,ambulance:112");
        numbers.put("Western Sahara" +
                "", "police:150,fire:150,ambulance:150");
        numbers.put("Zambia" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Zimbabwe" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Antarctica" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Abkhazia" +
                "", "police:102,fire:103,ambulance:101");
        numbers.put("Afghanistan" +
                "", "police:119,fire:112,ambulance:119");
        numbers.put("Akrotiri and Dhekelia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Bahrain" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Bangladesh" +
                "", "police:999,fire:199,ambulance:199");
        numbers.put("Bhutan" +
                "", "police:113,fire:112,ambulance:110");
        numbers.put("British Indian Ocean Territory" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Brunei" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Burma" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Cambodia" +
                "", "police:117,fire:119,ambulance:118");
        numbers.put("People's Republic of China" +
                "", "police:110,fire:120,ambulance:119");
        numbers.put("Christmas Island" +
                "", "police:000,fire:000,ambulance:000");
        numbers.put("Cocos Islands" +
                "", "police:000,fire:000,ambulance:000");
        numbers.put("East Timor" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Hong Kong" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("India" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Indonesia" +
                "", "police:110,fire:118,ambulance:113");
        numbers.put("Iran" +
                "", "police:110,fire:115,ambulance:125");
        numbers.put("Iraq" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Israel" +
                "", "police:100,fire:101,ambulance:102");
        numbers.put("Japan" +
                "", "police:110,fire:119,ambulance:119");
        numbers.put("Jordan" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Kazakhstan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Kyrgyzstan" +
                "", "police:102,fire:103,ambulance:101");
        numbers.put("Democratic People's Republic of Korea" +
                "", "police:119,fire:119,ambulance:119");
        numbers.put("Republic of Korea" +
                "", "police:112,fire:119,ambulance:119");
        numbers.put("Kurdistan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Kuwait" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Laos" +
                "", "police:191,fire:195,ambulance:190");
        numbers.put("Lebanon" +
                "", "police:112,fire:140,ambulance:175");
        numbers.put("Macau" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Maldives" +
                "", "police:119,fire:102,ambulance:118");
        numbers.put("Malaysia" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Mongolia" +
                "", "police:105,fire:105,ambulance:105");
        numbers.put("Nepal" +
                "", "police:100,fire:102,ambulance:101");
        numbers.put("Oman" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Pakistan" +
                "", "police:15,fire:115,ambulance:16");
        numbers.put("Philippines" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Qatar" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Saudi Arabia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Singapore" +
                "", "police:999,fire:985,ambulance:985");
        numbers.put("Sri Lanka" +
                "", "police:119,fire:110,ambulance:110");
        numbers.put("Syria" +
                "", "police:112,fire:110,ambulance:113");
        numbers.put("Republic of China" +
                "", "police:110,fire:119,ambulance:119");
        numbers.put("Tajikistan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Thailand" +
                "", "police:191,fire:1669,ambulance:199");
        numbers.put("Turkmenistan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("United Arab Emirates" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Uzbekistan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Vietnam" +
                "", "police:113,fire:115,ambulance:114");
        numbers.put("Yemen" +
                "", "police:194,fire:191,ambulance:191");
        numbers.put("Aland Islands" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Albania" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Andorra" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Armenia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Artsakh" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Austria" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Azerbaijan" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Belarus" +
                "", "police:102,fire:103,ambulance:101");
        numbers.put("Belgium" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Bosnia and Herzegovina" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Bulgaria" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Croatia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Cyprus" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Czech Republic" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Denmark" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Estonia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Faroe Islands" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Finland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("France" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Georgia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Germany" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Gibraltar" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Greece" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Greenland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Guernsey" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Hungary" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Iceland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Ireland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Isle of Man" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Italy" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Jersey" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Kosovo" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Latvia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Lithuania" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Liechtenstein" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Luxembourg" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Republic of Macedonia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Malta" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Moldova" +
                "", "police:902,fire:903,ambulance:901");
        numbers.put("Monaco" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Montenegro" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Netherlands" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Northern Cyprus" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Norway" +
                "", "police:112,fire:113,ambulance:110");
        numbers.put("Poland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Portugal" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Romania" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Russia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("San Marino" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Serbia" +
                "", "police:192,fire:194,ambulance:193");
        numbers.put("Slovakia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Slovenia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Spain" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Sweden" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Switzerland" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Transnistria" +
                "", "police:102,fire:103,ambulance:101");
        numbers.put("Turkey" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Ukraine" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("United Kingdom" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Vatican City" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("American Samoa" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Australia" +
                "", "police:000,fire:000,ambulance:000");
        numbers.put("Cook Islands" +
                "", "police:999,fire:998,ambulance:996");
        numbers.put("Fiji" +
                "", "police:000,fire:000,ambulance:000");
        numbers.put("French Polynesia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Guam" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Kiribati" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Marshall Islands" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Micronesia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Nauru" +
                "", "police:110,fire:111,ambulance:112");
        numbers.put("New Caledonia" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("New Zealand" +
                "", "police:111,fire:111,ambulance:111");
        numbers.put("Palau" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Papua New Guinea" +
                "", "police:112,fire:111,ambulance:110");
        numbers.put("Samoa" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Solomon Islands" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Tonga" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Tuvalu" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put(" Vanuatu" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Belize" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Clipperton Island" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Costa Rica" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Guatemala" +
                "", "police:110,fire:128,ambulance:122");
        numbers.put("El Salvador" +
                "", "police:911,fire:132,ambulance:913");
        numbers.put("Honduras" +
                "", "police:112,fire:195,ambulance:198");
        numbers.put("Nicaragua" +
                "", "police:118,fire:128,ambulance:115");
        numbers.put("Panama" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Antigua and Barbuda" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Anguilla" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Aruba" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("British Virgin Islands" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Cuba" +
                "", "police:106,fire:104,ambulance:105");
        numbers.put("Curacao" +
                "", "police:911,fire:012,ambulance:911");
        numbers.put("Dominica" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Guadeloupe" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put(" Grenada" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Martinique" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Montserrat" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Navassa Island" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Saint Kitts and Nevis" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Saint Lucia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Saint Vincent and the Grenadines" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("United States Virgin Islands" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Barbados" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Bonaire" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Cayman Islands" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Dominican Republic" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Haiti" +
                "", "police:114,fire:116,ambulance:115");
        numbers.put("Puerto Rico" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Trinidad and Tobago" +
                "", "police:911,fire:811,ambulance:990");
        numbers.put("Jamaica" +
                "", "police:119,fire:110,ambulance:110");
        numbers.put("Bermuda" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Canada" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Mexico" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Saint Pierre and Miquelon" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("United States of America" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("The Bahamas" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Argentina" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Bolivia" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Brazil" +
                "", "police:190,fire:192,ambulance:193");
        numbers.put("Chile" +
                "", "police:133,fire:131,ambulance:132");
        numbers.put("Colombia" +
                "", "police:123,fire:123,ambulance:123");
        numbers.put("Ecuador" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Falkland Islands" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("French Guiana" +
                "", "police:112,fire:112,ambulance:112");
        numbers.put("Guyana" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Paraguay" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Peru" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("South Georgia and the South Sandwich Islands" +
                "", "police:999,fire:999,ambulance:999");
        numbers.put("Suriname" +
                "", "police:115,fire:115,ambulance:115");
        numbers.put("Uruguay" +
                "", "police:911,fire:911,ambulance:911");
        numbers.put("Venezuela" +
                "", "police:911,fire:911,ambulance:911");



    }

    public Map<String, String> getNumbers() {
        return numbers;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
