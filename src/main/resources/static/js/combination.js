function draw() {
    var testdata = [
        ["", "Amount", "GP", "Quantity", "Fourth"],
        ["2018-12-12", 160, 40, 4, 80],
        ["2018-12-13", 500, 450, 6, 80],
        ["2018-12-14", 300, 250, 5, 80],
        ["2018-12-15", 300, 250, 5, 85],
        ["2018-12-16", 300, 250, 5, 75]
    ];

    testdata.find

}

// Load the data from the XML file
$.get('data.xml', function(xml) {

    // Split the lines
    var $xml = $(xml);

    // push categories
    $xml.find('categories item').each(function(i, category) {
        options.xAxis.categories.push($(category).text());
    });

    // push series
    $xml.find('series').each(function(i, series) {

        var seriesOptions = {
            name: $(series).find('name').text(),
            data: []
        };

        // push data points
        $(series).find('data point').each(function(i, point) {
            seriesOptions.data.push(
                parseInt($(point).text())
            );
        });

        // add it to the options
        options.series.push(seriesOptions);
    });

    var chart = new Highcharts.Chart(options);
});