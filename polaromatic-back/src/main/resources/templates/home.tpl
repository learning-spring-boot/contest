yieldUnescaped '<!DOCTYPE html>'

html {
    head {
        title "Polaromatic"

        link(rel: 'stylesheet', href: '/css/app.css')
        link(rel: 'stylesheet', href: '/css/gh-fork-ribbon.css')

        ['webjars/sockjs-client/0.3.4-1/sockjs.min.js',
         'webjars/stomp-websocket/2.3.1-1/stomp.min.js',
         'webjars/jquery/2.1.3/jquery.min.js',
         'webjars/handlebars/2.0.0-1/handlebars.min.js',
         'js/Connection.js']
        .each {
            yieldUnescaped "<script src='$it'></script>"
        }
    }

    body {
        div(class: 'github-fork-ribbon-wrapper right') {
            div(class: 'github-fork-ribbon') {
                a(href: 'https://github.com/lmivan/contest', 'Fork me on GitHub')
            }
        }

        div(id: 'header') {
            div(class: 'center') {
                a(href: 'https://github.com/lmivan/contest', target: 'blank') {
                    img(src: 'images/polaromatic-logo.png')
                }
                p('Polaromatic')
                span('Powered by Spring Boot')
            }
        }
        div(id: 'timeline', class: 'center')
    }

    script(id: 'photo-template', type: 'text/x-handlebars-template') {
        div(class: 'photo-cover') {
            div(class: 'photo', style: 'visibility:hidden; height:0') {
                img(src: '{{image}}')
            }
        }
    }

    yieldUnescaped "<script>Connection().start()</script>"
}