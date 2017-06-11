$(function () {

    console.log("loading students");

    function loadMyJSON() {
        $.getJSON("api/students/", function (data) {
            console.log(data)
            var msg = "Ola, I'm empty!"
            if (data.length > 0) {
                msg = data[0];
            }
            $(".intro-text").text(msg);
        });

    }

    loadMyJSON();

});
