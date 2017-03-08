$(function() {

    var renderer = new marked.Renderer();

    marked.setOptions({
        gfm: true,
        tables: true,
        breaks: false,
        pedantic: false,
        sanitize: true,
        smartLists: true,
        smartypants: false,
        langPrefix: '',
        highlight: function(code) {
            return hljs.highlightAuto(code).value;
        }
    });

    renderer.code = function(code, lang, escaped) {
        var lines = code.split(/\r\n|\r|\n/);
        var len = 0;
        if (lines == null) {
            len = code.length;
        } else {
            $.each(lines, function(index, val){
                if(len < val.length){
                    len = val.length;
                }
            });
        }

        var code = code.replace(/&/g, '&amp;')
                        .replace(/</g, '&lt;')
                        .replace(/>/g, '&gt;')
                        .replace(/"/g, '&quot;')
                        .replace(/'/g, '&#39;');

        if (!lang) {
            return '<pre><code style="'
                + ' display: block; word-wrap: normal; overflow-x: scroll;'
                + ' width: ' + len + 'rem; '
                + ' -webkit-text-size-adjust: none;'
                + '">'
                + code
                + '\n</code></pre>';
        }

        return '<pre><code class="'
            + lang
            + '" style="'
            + ' display: block; word-wrap: normal; overflow-x: scroll;'
            + ' width: ' + len + 'rem; '
            + ' -webkit-text-size-adjust: none;'
            + '">'
            + code
            + '\n</code></pre>';
    };

    parse = function(text, theme, codeScrollable) {
        if (text == "") {
            return false;
        }

        text = text.replace(/\\n/g, "\n");

        switch (theme) {
            case 'light':
                $('link').first().attr({
                    'rel': 'stylesheet',
                    'href': '../css/bootstrap-light.css'
                });
                $('link').last().attr({
                    'rel': 'stylesheet',
                    'href': '../css/atom-one-light.css'
                });
                break;
            case 'dark':
                $('link').first().attr({
                    'rel': 'stylesheet',
                    'href': '../css/bootstrap-dark.css'
                });
                $('link').last().attr({
                    'rel': 'stylesheet',
                    'href': '../css/atom-one-dark.css'
                });
                break;
            case 'green':
                $('body').css("background-color", "#B8FFEA")
                break;
            case 'cyan':
                $('body').css("background-color", "#C9FFFC");
                break;
            case 'pink':
                $('body').css("background-color", "#FFEBEF");
                break;
            case 'purple':
                $('body').css("background-color", "#F6EBFC");
                break;
            case 'wood':
                $('body').css("background-image", "url(wood.png)");
                break;
            case 'paper':
                $('body').css("background-image", "url(paper.png)");
                break;
        }

        // markdown html
        var html;
        if (!codeScrollable) {
            html = marked(text);
        } else {
            html = marked(text, {renderer: renderer});
        }

        $('#markdown').html(html);

        $('pre code').each(function(i, block) {
            hljs.highlightBlock(block);
        });
    };
});