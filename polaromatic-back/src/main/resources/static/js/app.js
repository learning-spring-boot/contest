function connect() {
    var source = $("#photo-template").html();
    var template = Handlebars.compile(source);

    var socket = new SockJS('/polaromatic');
    var client = Stomp.over(socket);
    client.debug = null;

    client.connect({}, function () {
        client.subscribe('/notifications/photo', function (message) {

            var context = {
                image: 'data:image/png;base64,' + message.body
            }

            var html = template(context);
            $('#timeline').prepend(html);

            $("#timeline .photo:first-child img").on("load", function () {
                $(this).parent().css({
                    display: 'none',
                    visibility: 'visible',
                    height: 'auto'
                });

                $(this).parent().slideDown();
            })
        });
    });
}