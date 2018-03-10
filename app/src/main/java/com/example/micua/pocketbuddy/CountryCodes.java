package com.example.micua.pocketbuddy;

import java.util.HashMap;
import java.util.Map;

public class CountryCodes {
    private String countries = "Afghanistan Albania Algeria Andorra Angola Antigua Argentina Armenia " +
            "Australia Austria Azerbaijan Bahamas Bahrain Bangladesh Barbados Belarus Belgium Belize Benin " +
            "Bhutan Bolivia Bosnia Botswana Brazil Brunei Bulgaria Burkina Burundi Cambodia Cameroon " +
            "Canada Cape-Verde Central-African-Rep Chad Chile China Colombia Comoros Congo Congo Costa-Rica " +
            "Croatia Cuba Cyprus Czech-Republic Denmark Djibouti Dominica Dominican-Republic East-Timor " +
            "Ecuador Egypt El-Salvador Equatorial-Guinea Eritrea Estonia Ethiopia Fiji Finland France Gabon Gambia Georgia Germany Ghana Greece Grenada " +
            "Guatemala Guinea Guinea-Bissau Guyana Haiti Honduras Hungary Iceland India Indonesia Iran Iraq Ireland " +
            "Israel Italy Ivory-Coast Jamaica Japan Jordan Kazakhstan Kenya Kiribati Korea-North Korea-South " +
            "Kosovo Kuwait Kyrgyzstan Laos Latvia Lebanon Lesotho Liberia Libya Liechtenstein Lithuania Luxembourg Macedonia " +
            "Madagascar Malawi Malaysia Maldives Mali Malta Marshall-Islands Mauritania Mauritius Mexico Micronesia Moldova " +
            "Monaco Mongolia Montenegro Morocco Mozambique Myanmar Namibia Nauru Nepal Netherlands New-Zealand Nicaragua Niger Nigeria Norway Oman " +
            "Pakistan Palau Panama Papua-New-Guinea Paraguay Peru Philippines Poland Portugal Qatar Romania Russian-Federation " +
            "Rwanda St-Kitts&Nevis St-Lucia Saint Vincent & the Grenadines Samoa San-Marino Sao-Tome&Principe Saudi-Arabia " +
            "Senegal Serbia Seychelles Sierra-Leone Singapore Slovakia Slovenia Solomon-Islands Somalia South-Africa " +
            "South-Sudan Spain Sri-Lanka Sudan Suriname Swaziland Sweden Switzerland Syria Taiwan Tajikistan Tanzania " +
            "Thailand Togo Tonga Trinidad&Tobago Tunisia Turkey Turkmenistan Tuvalu Uganda Ukraine United-Arab-Emirates " +
            "United-Kingdom United-States Uruguay Uzbekistan Vanuatu Vatican-City Venezuela Vietnam Yemen Zambia Zimbabwe";
    private String languages = "Azerbaijan Albanian Amharic English Arabic Armenian Afrikaans Basque Bashkir" +
            " Belarusian Bengali Burmese Bulgarian Bosnian Welsh Hungarian Vietnamese Haitian Galician Dutch" +
            " Greek Georgian Gujarati Danish Hebrew Yiddish Indonesian Irish Italian Icelandic Spanish" +
            " Kazakh Kannada Catalan Kyrgyz Chinese Korean Xhosa Khmer Laotian Latin Latvian Lithuanian" +
            " Luxembourgish Malagasy Malay Malayalam Maltese Macedonian Maori Marathi Mari Mongolian German" +
            " Nepali Norwegian Punjabi Papiamento Persian Polish Portuguese Romanian Russian Cebuano" +
            " Serbian Sinhala Slovakian Slovenian Swahili Sundanese Tajik Thai Tagalog Tamil Tatar Telugu" +
            " Turkish Uzbek Ukrainian Urdu Finnish French Hindi Croatian Czech Swedish Scottish Estonian" +
            " Javanese Japanese";
    private String[] langCodes = {"az", "sq", "am", "en", "ar", "hy", "af", "eu", "ba", "be", "bn", "my",
            "bg", "bs", "cy", "hu", "vi", "ht", "gl", "nl", "el", "ka", "gu", "da", "he", "yi",
            "id", "ga", "it", "is", "es", "kk", "kn", "ca", "ky", "zh", "ko", "xh", "km", "lo", "la",
            "lv", "lb", "mg", "ms", "ml", "mt", "mk", "mi", "mr", "mhr", "mn", "de", "ne", "no", "pa", "pap",
            "fa", "pl", "pt", "ro", "ru", "ceb", "sr", "si", "sk", "sl", "sw", "su", "tg", "th", "tl",
            "ta", "tt", "te", "tr", "uz", "uk", "ur", "fi", "fr", "hi", "hr","cs", "sv", "gd",
            "et", "jv", "ja"};
    private String[] languagesFinal;
    private String[] countriesFinal;
    private Map<String, String> countriesDict;
    public CountryCodes(){
        languagesFinal = languages.split(" ");
        countriesFinal = countries.split(" ");
        countriesDict = new HashMap<>();
    }
}
