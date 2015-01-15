package polaromatic

import org.grooscript.asts.GsNative

class Connection {
    @GsNative
    def initOn(source, path) {/*
        var socket = new SockJS(path);
        return [Handlebars.compile(source), Stomp.over(socket)];
    */}

    def start() {
        def source = $("#photo-template").html()
        def (template, client) = initOn(source, '/polaromatic')
        client.debug = null

        client.connect(gs.toJavascript([:])) { ->
            client.subscribe('/notifications/photo') { message ->
                def context = [image: 'data:image/png;base64,' + message.body]
                def html = template(context)
                $('#timeline').prepend(html)
                $("#timeline .photo:first-child img").on("load") {
                    $(this).parent().css(gs.toJavascript(display: 'none', visibility: 'visible', height: 'auto'))
                    $(this).parent().slideDown()
                }
            }
        }
    }
}
