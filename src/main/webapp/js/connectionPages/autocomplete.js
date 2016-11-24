$(document).ready(function() {
  var country1 = "";
  var city1 = "";
  var router1 = "";
  var country2 = "";
  var city2 = "";
  var router2 = "";

  var country1_options = {
    url: function(phrase) {
      country1 = phrase;
      console.log(country1);
      return "/autocomplete?field=country&query="+phrase;
    },
    getValue: "name"
  };
  var city1_options = {
    url: function(phrase) {
      city1 = phrase;
      console.log(city1);
      return "/autocomplete?field=city&query="+phrase+"&country="+$("#country1").val();
    },
    getValue: "name"
  };
  var router1_options = {
    url: function(phrase) {
      router1 = phrase;
      console.log(router1);
      return "/autocomplete?field=router&query="+phrase+"&city="+$("#city1").val();
    },
    getValue: "name"
  };

  var country2_options = {
    url: function(phrase) {
      country2 = phrase;
      console.log(country2);
      return "/autocomplete?field=country&query="+phrase;
    },
    getValue: "name"
  };
  var city2_options = {
    url: function(phrase) {
      city2 = phrase;
      console.log(city2);
      return "/autocomplete?field=city&query="+phrase+"&country="+$("#country2").val();
    },
    getValue: "name"
  };
  var router2_options = {
    url: function(phrase) {
      router2 = phrase;
      console.log(router2);
      return "/autocomplete?field=router&query="+phrase+"&city="+$("#city2").val();
    },
    getValue: "name"
  };

  $("#country1").easyAutocomplete(country1_options);
  $("#city1").easyAutocomplete(city1_options);
  $("#SN1").easyAutocomplete(router1_options);
  $("#country2").easyAutocomplete(country2_options);
  $("#city2").easyAutocomplete(city2_options);
  $("#SN2").easyAutocomplete(router2_options);
})
