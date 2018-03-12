package com.example.micua.pocketbuddy;

import java.util.HashMap;
import java.util.Map;

public class CountryCodes {
    private String countries = "Afghanistan Albania Algeria Andorra Angola Antigua Argentina Armenia " +
            "Australia Austria Azerbaijan Bahamas Bahrain Bangladesh Barbados Belarus Belgium Belize Benin " +
            "Bhutan Bolivia Bosnia Botswana Brazil Brunei Bulgaria Burkina Burundi Cambodia Cameroon " +
            "Canada Cape Central Chad Chile China Colombia Comoros Congo Costa-Rica " +
            "Croatia Cuba Cyprus Czech Denmark Djibouti Dominica Dominican East " +
            "Ecuador Egypt El Equatorial Eritrea Estonia Ethiopia Fiji Finland France Gabon Gambia Georgia Germany Ghana Greece Grenada " +
            "Guatemala Guinea Guyana Haiti Honduras Hungary Iceland India Indonesia Iran Iraq Ireland " +
            "Israel Italy Ivory Jamaica Japan Jordan Kazakhstan Kenya Kiribati Korea " +
            "Kosovo Kuwait Kyrgyzstan Laos Latvia Lebanon Lesotho Liberia Libya Liechtenstein Lithuania Luxembourg Macedonia " +
            "Madagascar Malawi Malaysia Maldives Mali Malta Marshall Mauritania Mauritius Mexico Micronesia Moldova " +
            "Monaco Mongolia Montenegro Morocco Mozambique Myanmar Namibia Nauru Nepal Netherlands New Nicaragua Niger Nigeria Norway Oman " +
            "Pakistan Palau Panama Papua Paraguay Peru Philippines Poland Portugal Qatar Romania Russian-Federation " +
            "Rwanda Saint Samoa San Sao Saudi " +
            "Senegal Serbia Seychelles Sierra Singapore Slovakia Slovenia Solomon Somalia South-Africa " +
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
            "lv", "lt", "lb", "mg", "ms", "ml", "mt", "mk", "mi", "mr", "mhr", "mn", "de", "ne", "no", "pa", "pap",
            "fa", "pl", "pt", "ro", "ru", "ceb", "sr", "si", "sk", "sl", "sw", "su", "tg", "th", "tl",
            "ta", "tt", "te", "tr", "uz", "uk", "ur", "fi", "fr", "hi", "hr", "cs", "sv", "gd",
            "et", "jv", "ja"};
    private String[] languagesFinal;
    private String[] countriesFinal;
    private Map<String, String> countriesDict;
    private String[] unsupportedCountriesForLocationTranslate = {"Bhutan", "Botswana", "Malawi", "Papua New Guinea"};

    public CountryCodes(){
        languagesFinal = languages.split(" ");
        countriesFinal = countries.split(" ");
        countriesDict = new HashMap<>();
        hashMapInit();
    }

    private void hashMapInit(){
        countriesDict.put("Afghanistan", "Persian");
        countriesDict.put("Albania", "Albanian Greek");
        countriesDict.put("Algeria", "Arabic French");
        countriesDict.put("Andorra", "Catalan French Portuguese");
        countriesDict.put("Angola", "Portuguese ");
        countriesDict.put("Antigua", "English");
        countriesDict.put("Argentina", "Spanish English Italian German French");
        countriesDict.put("Armenia", "Armenian Russian");
        countriesDict.put("Australia", "English");
        countriesDict.put("Austria", "German Slovenian Croatian Hungarian");
        countriesDict.put("Azerbaijan", "Azerbaijan Russian Armenian");
        countriesDict.put("Bahamas", "English Haitian");
        countriesDict.put("Bahrain", "Arabic English Urdu");
        countriesDict.put("Bangladesh", "English");
        countriesDict.put("Barbados", "English");
        countriesDict.put("Belarus", "Belarusian Russian");
        countriesDict.put("Belgium", "Dutch French ");
        countriesDict.put("Belize", "English Spanish Haitian");
        countriesDict.put("Benin", "French");
        countriesDict.put("Bolivia", "Spanish");
        countriesDict.put("Bosnia and Herzegovina", "Bosnian Croatian Serbian");
        countriesDict.put("Brazil", "Portuguese Spanish English French");
        countriesDict.put("Brunei", "Malayalam English Chinese");
        countriesDict.put("Bulgaria", "Bulgarian Turkish");
        countriesDict.put("Burkina Faso", "French");
        countriesDict.put("Burundi", "French Swahili");
        countriesDict.put("Cambodia", "Khmer French English");
        countriesDict.put("Cameroon", "French English");
        countriesDict.put("Canada", "English French");
        countriesDict.put("Cape Verde", "Portuguese");
        countriesDict.put("Central African Republic", "French");
        countriesDict.put("Chad", "French Arabic");
        countriesDict.put("Chile", "Spanish");
        countriesDict.put("China", "Chinese");
        countriesDict.put("Colombia", "Spanish");
        countriesDict.put("Comoros", "Arabic French");
        countriesDict.put("Congo", "French");
        countriesDict.put("Costa Rica", "Spanish English");
        countriesDict.put("Ivory Coast", "French");
        countriesDict.put("Croatia", "Croatian Italian Hungarian Czech Slovakian German");
        countriesDict.put("Cuba", "Spanish");
        countriesDict.put("Cyprus", "Greek Turkish English");
        countriesDict.put("Czech Republic", "Czech");
        countriesDict.put("Denmark", "Danish German");
        countriesDict.put("Djibouti", "French Arabic");
        countriesDict.put("Dominica", "English French");
        countriesDict.put("Dominican Republic", "Spanish");
        countriesDict.put("East Timor", "Portuguese Indonesian English");
        countriesDict.put("Ecuador", "Spanish");
        countriesDict.put("Egypt", "Arabic English French");
        countriesDict.put("El Salvador", "Spanish");
        countriesDict.put("Equatorial Guinea", "Spanish French English");
        countriesDict.put("Eritrea", "Arabic");
        countriesDict.put("Estonia", "Estonian Russian");
        countriesDict.put("Ethiopia", "Amharic Arabic English");
        countriesDict.put("Fiji", "English");
        countriesDict.put("Finland", "Finnish Swedish");
        countriesDict.put("France", "French");
        countriesDict.put("Gabon", "French");
        countriesDict.put("Gambia", "English");
        countriesDict.put("Georgia", "Georgian Russian Armenian Azerbaijan");
        countriesDict.put("Germany", "German");
        countriesDict.put("Ghana", "English");
        countriesDict.put("Greece", "Greek English French");
        countriesDict.put("Grenada", "English French");
        countriesDict.put("Guatemala", "Spanish");
        countriesDict.put("Guinea", "French Portuguese");
        countriesDict.put("Guyana", "English Haitian Hindi");
        countriesDict.put("Haiti", "Haitian French");
        countriesDict.put("Honduras", "Spanish");
        countriesDict.put("Hungary", "Persian Hungarian");
        countriesDict.put("Iceland", "Icelandic English German");
        countriesDict.put("India", "Hindi English Bengali");
        countriesDict.put("Indonesia", "English Dutch Javanese");
        countriesDict.put("Iran", "Persian");
        countriesDict.put("Iraq", "Arabic");
        countriesDict.put("Ireland", "English Irish");
        countriesDict.put("Israel", "Hebrew Arabic English");
        countriesDict.put("Italy", "Italian German French Slovenian");
        countriesDict.put("Jamaica", "English Haitian");
        countriesDict.put("Japan", "Japanese");
        countriesDict.put("Jordan", "Arabic English");
        countriesDict.put("Kazakhstan", "Kazakh Russian");
        countriesDict.put("Kenya", "English");
        countriesDict.put("Kiribati", "English");
        countriesDict.put("Korea", "Korean English");
        countriesDict.put("Kosovo", "Albanian Serbian Bosnian");
        countriesDict.put("Kuwait", "Arabic English");
        countriesDict.put("Kyrgyzstan", "Russian");
        countriesDict.put("Laos", "Laotian French English");
        countriesDict.put("Latvia", "Latvian Russian Lithuanian");
        countriesDict.put("Lebanon", "Arabic French English Armenian");
        countriesDict.put("Lesotho", "English");
        countriesDict.put("Liberia", "English");
        countriesDict.put("Libya", "Arabic Italian English");
        countriesDict.put("Liechtenstein", "German ");
        countriesDict.put("Lithuania", "Lithuanian Russian Polish");
        countriesDict.put("Luxembourg", "Luxembourgish French German ");
        countriesDict.put("Macedonia", "Macedonian Albanian Turkish");
        countriesDict.put("Madagascar", "French Malagasy");
        countriesDict.put("Malaysia", "English Chinese");
        countriesDict.put("Maldives", "English");
        countriesDict.put("Mali", "French");
        countriesDict.put("Malta", "Maltese English ");
        countriesDict.put("Marshall Islands", "English Japanese");
        countriesDict.put("Mauritania", "Arabic");
        countriesDict.put("Mauritius", "Persian");
        countriesDict.put("Mexico", "Spanish");
        countriesDict.put("Micronesia", "English");
        countriesDict.put("Moldova", "Romanian Russian");
        countriesDict.put("Monaco", "French English Italian");
        countriesDict.put("Mongolia", "Mongolian");
        countriesDict.put("Montenegro", "Serbian");
        countriesDict.put("Morocco", "Arabic French");
        countriesDict.put("Mozambique", "Portuguese");
        countriesDict.put("Myanmar", "Burmese");
        countriesDict.put("Nauru", "English");
        countriesDict.put("Nepal", "Nepali");
        countriesDict.put("Netherlands", "Dutch");
        countriesDict.put("New Zealand", "English Maori");
        countriesDict.put("Nicaragua", "Spanish English");
        countriesDict.put("Niger", "French");
        countriesDict.put("Nigeria", "English");
        countriesDict.put("Norway", "Norwegian");
        countriesDict.put("Oman", "Arabic English Urdu");
        countriesDict.put("Palau", "English");
        countriesDict.put("Panama", "Spanish English");
        countriesDict.put("Paraguay", "Spanish");
        countriesDict.put("Peru", "Spanish");
        countriesDict.put("Philippines", "English ");
        countriesDict.put("Poland", "Polish");
        countriesDict.put("Portugal", "Portuguese");
        countriesDict.put("Qatar", "Arabic English");
        countriesDict.put("Romania", "Romanian Hungarian German");
        countriesDict.put("Russia", "Russian");
        countriesDict.put("Rwanda", "French English");
        countriesDict.put("Saint", "English French");
        countriesDict.put("Samoa", "English");
        countriesDict.put("San Marino", "Italian");
        countriesDict.put("Sao Tome and Principe", "Portuguese");
        countriesDict.put("Saudi Arabia", "Arabic");
        countriesDict.put("Senegal", "French");
        countriesDict.put("Serbia", "Serbian Romanian Hungarian Slovakian Croatian");
        countriesDict.put("Seychelles", "English");
        countriesDict.put("Sierra Leone", "English");
        countriesDict.put("Singapore", "English Malay");
        countriesDict.put("Slovakia", "Slovakian Hungarian");
        countriesDict.put("Slovenia", "Slovenian");
        countriesDict.put("Solomon Islands", "English");
        countriesDict.put("Somalia", "Arabic English Italian");
        countriesDict.put("South Africa", "English ");
        countriesDict.put("South Sudan", "English Arabic");
        countriesDict.put("Spain", "Spanish Catalan");
        countriesDict.put("Sri Lanka", "Tamil");
        countriesDict.put("Sudan", "Arabic ");
        countriesDict.put("Suriname", "Dutch");
        countriesDict.put("Swaziland", "English");
        countriesDict.put("Sweden", "Swedish");
        countriesDict.put("Switzerland", "German French Italian");
        countriesDict.put("Syria", "Arabic Armenian");
        countriesDict.put("Taiwan", "Chinese");
        countriesDict.put("Tajikistan", "Russian");
        countriesDict.put("Tanzania", "Swahili English Arabic");
        countriesDict.put("Thailand", "English Thai");
        countriesDict.put("Togo", "French");
        countriesDict.put("Tonga", "English");
        countriesDict.put("Trinidad and Tobago", "English French Spanish Chinese Hindi");
        countriesDict.put("Tunisia", "Arabic French");
        countriesDict.put("Turkey", "Turkish");
        countriesDict.put("Turkmenistan", "Russian Uzbek");
        countriesDict.put("Tuvalu", "English");
        countriesDict.put("Uganda", "English");
        countriesDict.put("Ukraine", "Ukrainian Russian Romanian Polish Hungarian");
        countriesDict.put("United Arab Emirates", "Arabic Persian English Hindi Urdu");
        countriesDict.put("United Kingdom", "English Spanish");
        countriesDict.put("Uruguay", "Spanish");
        countriesDict.put("Uzbekistan", "Uzbek Russian");
        countriesDict.put("Vanuatu", "English French");
        countriesDict.put("Vatican City", "Italian Latin French");
        countriesDict.put("Venezuela", "Spanish");
        countriesDict.put("Vietnam", "Vietnamese English");
        countriesDict.put("Yemen", "Arabic");
        countriesDict.put("Zambia", "English");
        countriesDict.put("Zimbabwe", "English");
    }

    public String[] getUnsupportedCountriesForLocationTranslate() {
        return this.unsupportedCountriesForLocationTranslate;
    }

    public String[] getLangCodes() {
        return langCodes;
    }

    public String[] getLanguagesFinal() {
        return languagesFinal;
    }

    public Map<String, String> getCountriesDict() {
        return countriesDict;
    }

    public String[] getLangsForCountry(String country) {
        return countriesDict.get(country).split(" ");
    }
}
