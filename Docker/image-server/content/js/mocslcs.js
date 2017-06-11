$(function () {

    console.log("loading students");

    function loadMyJSON() {
        $.getJSON("api/students/", function (data) {
            console.log(data)
            var msg = "Ola, I'm empty!"
            if (data.length > 0) {
                msg = data[0].country +", "+ data[0].city + ", "+ data[0].number + " "+data[0].street;
            }
            $(".intro-text").text(msg);
        });

    }

    loadMyJSON();
    setInterval(loadMyJSON, 5000);
});
