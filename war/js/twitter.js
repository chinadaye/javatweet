window.currentpage = $("body").attr("id");
if (currentpage != "front" && currentpage != "create") {
    $("head").append('<script type="text/javascript" src="' + srcURL + 'lib/json2.js"/>');
    $(document.body).addClass("monkeyfly");
    window.user = $("meta[name=session-user-screen_name]").attr("content");
    window.pageuser = $("meta[name=page-user-screen_name]").attr("content");
    window.tinyURL = "http://api.bit.ly/shorten?version=2.0.1&login=egoing&apiKey=R_644d74992a0a12588389daff443ca860";
    window.tinyType = "bitly";
    window.t = "http://twitter.com/";
    window.profileRunInterval = 1000;
    window.suggestMaxInventory = 20;
    window.lastSyncInterval = 60 * 60 * 1000;
    window.lastSync4refreshInterval = 60 * 60 * 24 * 3 * 1000;
    window.is_MonkeyFly = true;
    Array.prototype.unique = function () {
        var c = [];
        var b = this.length;
        for (var e = 0; e < b; e++) {
            for (var d = e + 1; d < b; d++) {
                if (this[e] === this[d]) {
                    d = ++e
                }
            }
            c.push(this[e])
        }
        return c
    };
    Setting = function () {
        Setting.data;
        Setting.list = [{
            label: "Photo",
            id: "photo",
            init: true
        },
        {
            label: "URL Shortener",
            id: "tinyText",
            init: true
        },
        {
            label: "Links",
            id: "links",
            init: true
        },
        {
            label: "Suggest",
            id: "suggest",
            init: true
        },
        {
            label: "Profile",
            id: "profile",
            init: true
        },
        {
            label: "Tool Tip",
            id: "tooltip",
            init: true
        },
        {
            label: "Grid Panel",
            id: "grid",
            init: true,
            option: [{
                label: "Panel Width",
                id: "panel_width",
                type: "text",
                init: 320
            }]
        },
        {
            label: "CSS",
            id: "css",
            init: true,
            option: [{
                label: "description",
                id: "css_description",
                type: "textarea",
                init: ""
            }]
        },
        {
            label: "Korean Spell Check",
            id: "koreanSpellCheck",
            init: false
        },
        {
            label: "Shortcut",
            id: "shortcut",
            init: true
        }];
        Setting.init = function () {
            $("#home_link").ready(function (e) {
                $("#home_link").parent().after('<li><a id="monkeyFlySetting" href="http://monkeyfly.egoing.net" target="_blank">MonkeyFly</a></li>')
            });
            $("#monkeyFlySetting").click(function () {
                Setting.run();
                return false
            });
            $("#monkeyfly_settings_save").livequery("click", function () {
                data = new Object();
                for (var i = 0; i < Setting.list.length; i++) {
                    var list = Setting.list[i];
                    var c = Setting.list[i];
                    if (data[c.id] == null) {
                        data[c.id] = new Object()
                    }
                    data[c.id].value = eval($(":radio[name=" + c.id + "]:checked").val());
                    if (c.option != undefined) {
                        var subs = c.option;
                        for (var j = 0; j < subs.length; j++) {
                            data[c.id][subs[j].id] = $("#user_monkeyfly_" + c.id + "_" + subs[j].id).val()
                        }
                    }
                }
                $.cookie("config_" + user, JSON.stringify(data), {
                    expires: 10000000,
                    path: "/"
                });
                Setting.data = data;
                location.reload()
            });
            $("#monkeyfly_settings_cancel").livequery("click", function () {
                $("#settingPanel").remove();
                $("#mask").addClass("hide").removeClass("show")
            });
            Setting.data = new Object();
            if ($.cookie("config_" + user) == null || $.cookie("config_" + user) == "") {
                for (var i = 0; i < Setting.list.length; i++) {
                    Setting.data[Setting.list[i].id] = {
                        value: Setting.list[i].init
                    }
                }
            } else {
                var _d = JSON.parse($.cookie("config_" + user));
                for (var i = 0; i < _d.length; i++) {
                    Setting.data[_d] = _d[i]
                }
            }
        };
        Setting.init();
        Setting.run = function () {
            $(document.body).attr("id", "monkeyflySetting");
            var str = '<div id="settingPanel"><h1 id="settingTitle">MonkeyFly Setting</h1><fieldset class="common-form standard-form monkeyfly_setting"><table cellspacing="0" class="input-form"><tbody>';
            for (var i = 0; i < Setting.list.length; i++) {
                var list = Setting.list;
                var c = Setting.list[i];
                if (Setting.get(c.id).value) {
                    enable = 'checked="checked"';
                    disable = ""
                } else {
                    enable = "";
                    disable = 'checked="checked"'
                }
                str += Setting.element(c.label, '<input type="radio" value="true" name="' + c.id + '" id="user_monkeyfly_' + c.id + '_1" ' + enable + '/> <label for="user_monkeyfly_' + c.id + '_1">Enable</label> <input type="radio" value="false" name="' + c.id + '" id="user_monkeyfly_' + c.id + '_0" ' + disable + '/> <label for="user_monkeyfly_' + c.id + '_0">Disable</label>', null, "main");
                if (c.option != undefined) {
                    var s = c.option;
                    for (var j = 0; j < s.length; j++) {
                        var value = Setting.get(c.id)[s[j].id] == undefined ? s[j].init : Setting.get(c.id)[s[j].id];
                        if (s[j].type == "text") {
                            str += Setting.element(s[j].label, '<input class="sub" type="text" value="' + value + '" id="user_monkeyfly_' + c.id + "_" + s[j].id + '" name="' + s[j].id + '" />', null, "sub")
                        } else {
                            str += Setting.element(s[j].label, '<textarea class="sub" id="user_monkeyfly_' + c.id + "_" + s[j].id + '" name="' + s[j].id + '" >' + value + "</textarea>", null, "sub")
                        }
                    }
                }
            }
            str += '<tr>				  <th/>				  <td><input type="submit" value="Save" name="commit" id="monkeyfly_settings_save" class="btn btn-m"/> <input type="submit" value="Cancel" name="commit" id="monkeyfly_settings_cancel" class="btn btn-m"/></td>				</tr>			  </tbody></table>			</fieldset></div>';
            $("body").append(str);
            $("#mask").addClass("show");
            $(window).resize(function () {
                Setting.repaint()
            });
            $("#settingPanel textarea").focus(function () {
                $(this).css("height", "400px")
            }).blur(function () {
                $(this).css("height", "30px")
            });
            Setting.repaint()
        };
        Setting.repaint = function () {
            $("#settingPanel").css("left", ($(window).width() / 2 - $("#settingPanel").width() / 2) + "px").css("height", ($(window).height() - 40) + "px").css("top", (10) + "px")
        };
        Setting.get = function (name) {
            var D = JSON.parse($.cookie("config_" + user));
            try {
                if (D == null || D[name] == null) {
                    for (var i = 0; i < Setting.list.length; i++) {
                        if (Setting.list[i].id == name) {
                            var c = Setting.list[i];
                            if (c.id == name) {
                                var E = {
                                    value: c.init
                                };
                                for (var _name in c.option) {
                                    var _c = c.option[_name];
                                    E[_c.id] = _c.init
                                }
                                return E
                            }
                            break
                        }
                    }
                } else {
                    return D[name]
                }
            } catch(e) {
                console.log(e)
            }
            return {
                value: null
            }
        };
        Setting.element = function (label, field, description, type) {
            if (type == "main") {
                var str = '<tr class="' + type + '">				  <th><label for="user_name">' + label + "</label></th>				  <td>					" + field + "					" + (description == null ? "" : "<br/><small>" + description + "</small>") + "				  </td>				</tr>"
            } else {
                var str = '					<tr class="' + type + '">					  <th></th>					  <td>					  	<table>							<tr>								<td>									' + label + "<br />									" + field + "									" + (description == null ? "" : "<br/><small>" + description + "</small>") + "								</td>							<tr>						</table>					  </td>					</tr>"
            }
            return str
        }
    };
    Setting();

    function convertTinyURL(b) {
        if (!b.result && b.original.length > b.tinyURL) {
            return
        }
        var a = "#status";
        if ($("body").attr("id") == "direct_messages") {
            a = "#direct_message_form #text"
        }
        $(a).val($(a).val().replace(b.original, b.tinyURL));
        Links.add(b);
        $("#pagination #links_nest").pagination(Links.data.length, {
            callback: Links.pageselectCallback,
            items_per_page: Links.pageScale,
            num_display_entries: 10
        })
    }
    function insertPhoto(a) {
        var b = "#status";
        if ($("body").attr("id") == "direct_messages") {
            b = "#direct_message_form #text"
        }
        $(b).val($(b).val() + a)
    }
    function login(a, d, b) {
        popup("profile", "show");
        var c = getMovieName("profileSession");
        c.login(a, d, b ? true : false)
    }
    function onLoginComplete(b) {
        switch (b.target) {
        case "twitpic":
            try {
                popup("profile", "hide");
                var a = getMovieName("twitpic");
                for (i = 0; i < a.length; i++) {
                    try {
                        a[i].run()
                    } catch(b) {
                        console.log(b)
                    }
                }
            } catch(b) {
                console.log(b)
            }
            break
        }
    }
    function popup(a, b) {
        switch (a) {
        case "profile":
            if (b == "show") {
                $("#status_login_box").addClass("show");
                $("#mask").addClass("show")
            } else {
                $("#status_login_box").removeClass("show");
                $("#mask").removeClass("show")
            }
            break;
        case "externalWindow":
            if (b == "show") {
                $("#externalWindow").addClass("show")
            } else {
                $("#externalWindow").removeClass("show")
            }
        }
        repaint()
    }
    function getMovieName(a) {
        if (navigator.appName.indexOf("Microsoft") != -1) {
            return window[a]
        } else {
            return document[a]
        }
    } (function (a) {
        a.fajax = function (d) {
            var b = getMovieName("fhttpRequest");
            a.fajax.list.push(d);
            var c = a.fajax.list.length - 1;
            b.ajax({
                type: "POST",
                url: encodeURIComponent(d.url),
                data: d.data,
                id: c,
                callback: "$.fajax.callback"
            })
        };
        a.fajax.callback = function (c, b) {
            a.fajax.list[c].success(b.data)
        };
        a.fajax.list = new Array()
    })(jQuery);

    function Friends() {
        var b = 0;
        var a = this;
        Friends.fi = null;
        Friends.flag = true;
        Friends.lastSyncDate = null;
        this.getValue = function (j) {
            if (Friends.fi == undefined) {
                try {
                    Friends.lastSyncDate = getMovieName("fcookie").getValue("lastSyncDate");
                    Friends.fi = getMovieName("fcookie").getValue("following");
                    var d = Friends.lastSyncDate;
                    var h = ((new Date()).getTime());
                    var f = window.lastSync4refreshInterval;
                    if (d != null && d + f < h) {
                        if (Friends.flag) {
                            Friends.sync(j)
                        }
                        Friends.flag = false;
                        return Friends.fi
                    }
                } catch(g) {
                    return []
                }
                if (Friends.fi == null) {
                    var d = Friends.lastSyncDate;
                    var h = ((new Date()).getTime());
                    var f = window.lastSyncInterval;
                    if (d != null && d + f > h) {
                        return []
                    }
                    if (Friends.flag) {
                        Friends.sync(j)
                    }
                    Friends.flag = false
                }
            }
            return Friends.fi
        };
        this.setValue = function (c, d) {
            getMovieName("fcookie").setValue(c, d)
        };
        this.popularity = function (g, c) {
            var f = a.getValue();
            for (var e = 0; e < f.length; e++) {
                if (f[e].username == g) {
                    if (Friends.fi[e].popularity == undefined) {
                        Friends.fi[e].popularity = 0
                    }
                    Friends.fi[e].popularity = (Friends.fi[e].popularity + c);
                    this.sort();
                    return
                }
            }
        };
        this.sortMan = function (d, c) {
            if (d.popularity < c.popularity) {
                return 1
            } else {
                return -1
            }
        };
        this.sort = function () {
            var c = a.getValue();
            c.sort(a.sortMan);
            this.setValue("following", c)
        };
        Friends.getRelation = function (c, d) {
            if (Friends.getRelation.comm != undefined) {
                Friends.getRelation.comm.abort()
            }
            Friends.getRelation.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/" + c,
                dataType: "html/text",
                success: function (g) {
                    try {
                        var j = /<div id="user_(\d+)" class="user (following|blocking)?\s?(direct-messageable)?"/;
                        var f = g.match(j);
                        relation = "none";
                        if (f[3] != undefined && f[3] == "direct-messageable") {
                            if (f[2] == "following") {
                                relation = "friend"
                            } else {
                                relation = "follower"
                            }
                        } else {
                            if (f[2] == "following") {
                                relation = "following"
                            }
                        }
                        d(relation)
                    } catch(h) {
                        if (g.indexOf('id="cancel_request"') > -1) {
                            d("requested_protected")
                        } else {
                            if (g.indexOf('id="send_request"') > -1) {
                                d("protected")
                            } else {
                                d("nothing")
                            }
                        }
                    }
                }
            })
        };
        Friends.create = function (c, d) {
            if (Friends.create.comm != undefined) {
                Friends.create.comm.abort()
            }
            Friends.create.comm = $.ajax({
                type: "POST",
                dataType: "json",
                url: "/friendships/create/" + c,
                data: {
                    authenticity_token: twttr.form_authenticity_token,
                    twttr: true
                },
                success: function (e) {
                    d(e)
                }
            })
        };
        Friends.destory = function (c, d) {
            if (Friends.destory.comm != undefined) {
                Friends.destory.comm.abort()
            }
            Friends.destory.comm = $.ajax({
                type: "POST",
                dataType: "json",
                url: "/friendships/destroy/" + c,
                data: {
                    authenticity_token: twttr.form_authenticity_token,
                    twttr: true
                },
                success: function (e) {
                    d(e)
                }
            })
        };
        Friends.sync = function (e, d) {
            var c = new Array();
            if (this.comm != undefined) {
                this.comm.abort()
            }
            this.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/following" + (d == null ? "" : "?page=" + d),
                success: function (l) {
                    window._data = l;
                    var o = (new Date()).getTime();
                    getMovieName("fcookie").setValue("lastSyncDate", o);
                    Friends.lastSyncDate = o;
                    var f = l.match(/(<td class="user-detail">[\s\S]+?<\/td>)/mg);
                    for (h = 0; h < f.length; h++) {
                        try {
                            var q = f[h].match(/<span class="label fullname">([\s\S]+?)<\/span>/m)
                        } catch(m) {
                            console.log(m)
                        }
                        try {
                            var j = f[h].match(/<a href[\s\S]+title="(\w+)"[\s\S]*?<\/a>/m)
                        } catch(m) {
                            console.log(m)
                        }
                        try {
                            var v = j != null ? j[1] : "";
                            var w = q != null ? q[1] : "";
                            var r = false;
                            for (var g = 0; g < Friends.fi.length; g++) {
                                if (Friends.fi[g].username == v) {
                                    r = true;
                                    break
                                }
                            }
                            if (r) {
                                continue
                            }
                            c.push({
                                username: v,
                                screenname: w,
                                popularity: b++
                            })
                        } catch(m) {
                            console.log(m)
                        }
                    }
                    try {
                        if (Friends.fi == null) {
                            Friends.fi = c
                        } else {
                            for (var h = 0; h < c.length; h++) {
                                Friends.fi.push(c[h])
                            }
                        }
                        getMovieName("fcookie").setValue("following", Friends.fi);
                        if (e != undefined) {
                            e(Friends.fi)
                        }
                    } catch(m) {
                        console.log(m)
                    }
                    try {
                        var n = l.match(/<a href="\/following\?page=(\d+)" class="section_links"/m);
                        if (n == null) {
                            return
                        } else {
                            Friends.sync(null, n[1])
                        }
                    } catch(m) {}
                }
            })
        }
    }
    function Suggest(j, h, g) {
        var j = j;
        this.input = h;
        var d = 0;
        var a = 0;
        var b = false;
        this.keyIgnore = false;
        this.keyIgnorePattern = /\W/;
        var c = new Array();
        var f = g;
        var e;
        Suggest.id = 0;
        this.id = 0;
        var l = this;
        (function k() {
            $(j).ready(function () {
                id = ++Suggest.id;
                $(j).prepend('<div style="-moz-user-select: none; display:none;" class="suggest" role="listbox" id="monkey_suggest' + id + '"></div>');
                e = $("#monkey_suggest" + id)
            });
            $("#monkey_suggest" + id + " .element").livequery("mouseover", function () {
                var m = $(this);
                l.focus(Number(m.attr("index")) + 1)
            }).livequery("mouseout", function () {
                $(this).removeClass("selected");
                clearTimeout(ToolTip.delayOver);
                clearTimeout(ToolTip.delayOut);
                ToolTip.delayOut = setTimeout("ToolTip.close()", 500)
            }).livequery("click", function () {
                d = Number($(this).attr("index")) + 1;
                l.select();
                l.hide()
            })
        })();
        this.items = function (x) {
            var y = "";
            d = 1;
            c = x;
            for (var v = 0; v < x.length; v++) {
                a = v;
                var n = x[v].matching;
                var r = n.toLowerCase();
                var o = x[v].username.toLowerCase();
                var q = '<b class="ac-highlighted" style="-moz-user-select: none;">' + x[v].username.substr(0, n.length) + "</b>" + x[v].username.substr(n.length);
                if (x[v].screenname.length > 0) {
                    var w = '" &lt;' + x[v].screenname + "&gt;"
                } else {
                    var w = ""
                }
                y += '				<div role="option" index="' + v + '" class="element' + (v == 0 ? " selected" : "") + '" style="-moz-user-select: none;" username="' + x[v].username + '" matching="' + x[v].matching + '" screenname="' + x[v].screenname + '" front="' + encodeURI(x[v].front) + '" back="' + encodeURI(x[v].back) + '">					<div class="aq" style="-moz-user-select: none;">					  <div class="al" style="-moz-user-select: none;">						<div class="ak de" style="-moz-user-select: none;"/>					  </div>					  <div class="am" style="-moz-user-select: none;">"@' + q + w + "</div>					</div>				</div>"
            }
            $("#monkey_suggest" + id).show();
            $("#monkey_suggest" + id).css("left", $("#status").position().left + "px").css("top", ($("#status").position().top + $("#status").height() + 10) + "px");
            b = true;
            $("#monkey_suggest" + id).html(y)
        };
        $(h).livequery("keydown", function (m) {
            g(m, {
                type: "keydown",
                target: l
            })
        }).livequery("keypress", function (m) {
            g(m, {
                type: "keypress",
                target: l
            })
        }).livequery("keyup", function (m) {
            g(m, {
                type: "keyup",
                target: l
            })
        });
        this.hide = function () {
            if (!b) {
                return true
            }
            b = false;
            $("#monkey_suggest" + id).hide();
            ToolTip.close()
        };
        this.isActive = function () {
            return b
        };
        this.select = function () {
            var m = $("#monkey_suggest" + id + " div.element:nth-child(" + (d) + ")");
            return g(new Object(), {
                type: "selected",
                target: l,
                selected: {
                    username: m.attr("username"),
                    screenname: m.attr("screenname"),
                    matching: m.attr("matching"),
                    back: m.attr("back"),
                    front: m.attr("front")
                }
            })
        };
        this.focus = function (m) {
            d = Math.min(Math.max(m, 1), a + 1);
            $("#monkey_suggest" + id + " div.element").removeClass("selected");
            var n = $("#monkey_suggest" + id + " div.element:nth-child(" + d + ")");
            n.addClass("selected");
            try {
                ToolTip.context = false;
                var r = $(n[0]).attr("username");
                var q = e.offset();
                clearTimeout(ToolTip.delayOver);
                ToolTip.delayOver = setTimeout('ToolTip.open("' + r + '",' + Math.ceil(q.top + 4) + "," + Math.ceil(q.left + e.width() + 8) + ")", window.profileRunInterval)
            } catch(o) {
                console.log(o)
            }
        };
        this.up = function () {
            this.focus(d - 1)
        };
        this.down = function () {
            this.focus(d + 1)
        }
    }
    function suggetsListener(c, h) {
        suggetsListener.atReg = /@\w{0,}$/;
        switch (h.type) {
        case "keydown":
            _k = c.keyCode;
            switch (_k) {
            case 13:
                if (!h.target.isActive()) {
                    return
                }
                var n = h.target.select();
                if (h.target.isActive) {
                    c.preventDefault()
                }
                break;
            case 38:
                if (!h.target.isActive()) {
                    return
                }
                h.target.up();
                h.target.keyIgnore = true;
                c.preventDefault();
                return;
            case 40:
                if (!h.target.isActive()) {
                    return
                }
                h.target.down();
                h.target.keyIgnore = true;
                c.preventDefault();
                return
            }
            h.target.keyIgnore = false;
            h.target.keyIgnore = h.target.keyIgnorePattern.test(String.fromCharCode(_k));
            switch (_k) {
            case 229:
            case 21:
            case 16:
                return;
            case 8:
                h.target.keyIgnore = false;
                return
            }
            if (h.target.keyIgnore) {
                h.target.hide();
                h.target.keyIgnore = true;
                return
            }
            break;
        case "keypress":
            break;
        case "keyup":
            _k = c.keyCode;
            text = $(h.target.input).val();
            if (text.indexOf("@") == -1) {
                h.target.hide();
                return
            }
            switch (_k) {
            case 229:
            case 21:
            case 16:
                return
            }
            if (h.target.keyIgnore) {
                switch (_k) {
                case 38:
                case 40:
                    return
                }
                h.target.hide();
                return
            }
            selection = $(h.target.input).getSelection();
            front = text.substring(0, selection.end);
            back = text.substring(selection.end, text.length);
            var o = front.match(suggetsListener.atReg);
            if (o == null) {
                h.target.hide();
                return
            }
            k = fri.getValue();
            if (k == null) {
                h.target.hide();
                return
            }
            var e = new Array();
            for (var j = 0; j < k.length; j++) {
                if (e.length >= window.suggestMaxInventory) {
                    break
                }
                var g = k[j].username;
                var m = k[j].screenname;
                var a = o[0].substr(1);
                var d = a;
                if (g.toLowerCase().indexOf(d.toLowerCase()) == 0) {
                    e.push({
                        username: g,
                        matching: a,
                        screenname: m,
                        front: front,
                        back: back
                    })
                }
            }
            if (e.length > 0) {
                h.target.items(e)
            } else {
                if (e.length == 0) {
                    h.target.hide()
                }
            }
            break;
        case "selected":
            var n = h.selected;
            var k = decodeURI(n.front);
            var l = decodeURI(n.back);
            k = k.substr(0, k.lastIndexOf("@")) + "@" + n.username + " ";
            l = l.replace(/\w/, "");
            $("#status").val(k + l);
            h.target.hide();
            fri.popularity(n.username, 10000);
            h.target.keyIgnore = true;
            $("#status").focus();
            break
        }
    } (function () {
        var a = {
            getSelection: function () {
                var b = this.jquery ? this[0] : this;
                return (("selectionStart" in b &&
                function () {
                    var c = b.selectionEnd - b.selectionStart;
                    return {
                        start: b.selectionStart,
                        end: b.selectionEnd,
                        length: c,
                        text: b.value.substr(b.selectionStart, c)
                    }
                }) || (document.selection &&
                function () {
                    b.focus();
                    var d = document.selection.createRange();
                    if (d == null) {
                        return {
                            start: 0,
                            end: b.value.length,
                            length: 0
                        }
                    }
                    var c = b.createTextRange();
                    var e = c.duplicate();
                    c.moveToBookmark(d.getBookmark());
                    e.setEndPoint("EndToStart", c);
                    return {
                        start: e.text.length,
                        end: e.text.length + d.text.length,
                        length: d.text.length,
                        text: d.text
                    }
                }) ||
                function () {
                    return {
                        start: 0,
                        end: b.value.length,
                        length: 0
                    }
                })()
            },
            replaceSelection: function () {
                var b = this.jquery ? this[0] : this;
                var c = arguments[0] || "";
                return (("selectionStart" in b &&
                function () {
                    b.value = b.value.substr(0, b.selectionStart) + c + b.value.substr(b.selectionEnd, b.value.length);
                    return this
                }) || (document.selection &&
                function () {
                    b.focus();
                    document.selection.createRange().text = c;
                    return this
                }) ||
                function () {
                    b.value += c;
                    return this
                })()
            }
        };
        jQuery.each(a, function (b) {
            jQuery.fn[b] = this
        })
    })();
    (function (d) {
        d.fn.watch = function (e, f) {
            for (var g = 0; g < this.length; g++) {
                var h = this[g];
                a(h, e, f)
            }
        };
        var b = [];
        d(c);

        function c() {
            try {
                var e;
                for (var f = 0; f < b.length; f++) {
                    e = b[f];
                    if (e.obj[e.prop] != e.last || typeof(e.obj[e.prop]) != typeof(e.last)) {
                        e.exec.call(e.obj, e.prop, e.last, e.obj[e.prop]);
                        e.last = e.obj[e.prop]
                    }
                }
            } catch(g) {}
            watchTimer = window.setTimeout(c, 500)
        }
        function a(g, e, f) {
            b.push({
                obj: g,
                prop: e,
                last: g[e],
                exec: f
            })
        }
    })(jQuery);
    $(document.body).ready(function () {
        fri = new Friends();
        if (Setting.get("suggest").value) {
            sugg = new Suggest("div.info", "#status", suggetsListener)
        }
    });
    $(document.body).prepend('<embed class="hidden_flash" src="' + srcURL + "cookie.swf?" + seed + '" quality="high" bgcolor="#ffffff" width="1" height="1" name="fcookie" id="fcookie" flashvars="&id=' + user + '" align="middle" allowScriptAccess="always" allowFullScreen="false" scaleMode="noScale" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" wmode="transparent" style="left:0;position:fixed;top:0;"/><embed class="hidden_flash" src="' + srcURL + "httpRequest.swf?" + seed + '" quality="high" bgcolor="#ffffff" width="1" height="1" name="fhttpRequest" id="fhttpRequest" align="middle" allowScriptAccess="always" allowFullScreen="false" scaleMode="noScale" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" wmode="transparent" style="left:0;position:fixed;top:0;"/>');
    $("#fcookie").load(function () {
        var a = getMovieName("fcookie").getValue("following");
        if (a == undefined) {
            syncFriends()
        } else {
            window.friendsInfo = a
        }
    });
    if (Setting.get("tinyText").value) {
        $(document.body).append('<embed src="' + srcURL + "tinyURL.swf?tinyURL=" + escape(tinyURL) + "&tinyType=" + escape(tinyType) + '" quality="high" bgcolor="#ffffff"  width="1" height="1" name="tinyURL" wmode="transparent" id="tinyURL" align="middle" allowScriptAccess="always" allowFullScreen="false" scaleMode="noScale" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" style="visibility:none;" /><div id="mask"></div>');
        $("div.status-btn").append('<input type="submit" tabindex="2" class="status-btn round-btn tinyTextSubmit" id="tinyTextSubmit" value="URL shortener" name="update"/>')
    }
    if (Setting.get("koreanSpellCheck").value) {
        $("div.status-btn").append('<input type="submit" tabindex="2" class="status-btn round-btn spellcheckSubmit" id="spellcheckSubmit" value="spell check" name="update"/>');
        $(document.body).append('<form id="spellcheck_form" target="externalContentHolder" action="' + srcURL + 'control.php?mode=spellCheck" method="POST">	<input id="spellcheck_content" type="hidden" name="data"></input></form><div id="command_view"></div>')
    }
    if (Setting.get("photo").value) {
        $(document.body).append('<div id="status_login_box"><embed src="' + srcURL + "profile.swf?" + seed + '" quality="high" bgcolor="#ffffff" width="100%" height="100%" name="profileSession" wmode="transparent" id="profileSession" flashvars="&id=' + user + '" align="middle" allowScriptAccess="always" allowFullScreen="false" scaleMode="noScale" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" wmode="transparent" /></div>');
        $("div.status-btn").append('<embed src="' + srcURL + 'twitpic.swf" flashvars=&id=' + user + "&url=" + srcURL + "&" + seed + '" quality="high" bgcolor="#ffffff" width="115" height="32" name="twitpic" id="twitpic" wmode="transparent"  align="middle" allowScriptAccess="always" allowFullScreen="true" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" />')
    }
    if (Setting.get("css").value) {
        $("head").append('<style type="text/css">' + Setting.get("css").css_description + "</style>")
    }
    function Links() {
        Links.pageScale = 10;
        Links.data;
        (function () {
            setTimeout("Links.init(0)", 100)
        })();
        Links.init = function (a) {
            var a = (a == undefined ? 0 : a);
            if (getMovieName("fcookie").getValue == undefined) {
                if (a < 20) {
                    setTimeout("Links.init(" + (++a) + ")", 500)
                }
                return false
            }
            Links.data = getMovieName("fcookie").getValue("links");
            if (Links.data != undefined && Links.data.length > 0 && $("#links_tab").length == 0) {
                $("#favorites_tab").after('<li id="links_tab" class=""><a id="favorites_link" class="in-page-link"><span>Links</span></a></li>');
                $("#links_tab").click(function () {
                    Links.run();
                    return false
                })
            }
        };
        Links.run = function () {
            $("#primary_nav li").removeClass("active");
            $("#links_tab").addClass("active");
            $(document.body).attr("id", "links");
            $("#pagination").html('<table><tr><td><div id="links_nest"></div></td></tr></table>');
            $("#pagination #links_nest").pagination(Links.data.length, {
                callback: Links.pageselectCallback,
                items_per_page: Links.pageScale,
                num_display_entries: 10
            });
            $("#timeline_heading h1").text("Links");
            $("#timeline").innerHTML = "";
            var a = getMovieName("fcookie").getValue("links")
        };
        Links.pageselectCallback = function (c, f) {
            var a = getMovieName("fcookie").getValue("links");
            var e = Math.min((c + 1) * Links.pageScale, a.length);
            var d = "";
            for (var b = c * Links.pageScale; b < e; b++) {
                d += '<li class="list_list status" index="' + b + '" tinyURL="' + a[b].tinyURL + '"><span class="title"><a class="hentry status search_result" href="' + a[b].tinyURL + '" target="_blank">' + (a[b].original).substr(0, 65) + '</a></span><span class="clicks"></span></li>'
            }
            $("#timeline").html(d);
            Links.stat();
            return false
        };
        Links.stat = function () {
            $("#timeline li").each(function () {
                var c = $(this);
                var a = c.attr("index");
                try {
                    $.fajax({
                        url: "http://api.bit.ly/stats?version=2.0.1&shortUrl=" + $(this).attr("tinyURL") + "&login=egoing&apiKey=R_644d74992a0a12588389daff443ca860",
                        success: function (d) {
                            var d = JSON.parse(d);
                            $("li[index=" + a + "] .clicks").html(' <a href="' + c.attr("tinyURL") + '+" target="_blank">(clicks:' + d.results.clicks + ")</a>")
                        },
                        index: a
                    })
                } catch(b) {
                    console.log(b)
                }
                if (Links.data[a].htmlTitle == undefined) {
                    $.fajax({
                        url: "http://api.bit.ly/info?version=2.0.1&shortUrl=" + $(this).attr("tinyURL") + "&login=egoing&apiKey=R_644d74992a0a12588389daff443ca860",
                        success: function (f) {
                            var f = JSON.parse(f);
                            var d = $(c).attr("tinyURL").match(/http:\/\/.+?\/(.+)/);
                            var e = $.trim(f.results[d[1]]["htmlTitle"]);
                            if (e != "") {
                                Links.data[this.index] = {
                                    original: Links.data[this.index].original,
                                    result: true,
                                    tinyURL: Links.data[this.index].tinyURL,
                                    htmlTitle: e == "" ? Links.data[this.index].original : e
                                };
                                getMovieName("fcookie").setValue("links", Links.data);
                                if (e.length > 0) {
                                    $(c).find("li[index=" + this.index + "]").html(f.results[d[1]]["htmlTitle"])
                                }
                            }
                        },
                        index: a
                    })
                } else {
                    $("li[index=" + a + "] a").html(Links.data[a]["htmlTitle"])
                }
            })
        };
        Links.add = function (d) {
            try {
                if (Links.data != undefined) {
                    for (var b = 0; b < Links.data.length; b++) {
                        for (var a = 0; a < Links.data.length; a++) {
                            if (Links.data[b].tinyURL == d.tinyURL) {
                                return
                            }
                        }
                    }
                    Links.data.unshift(d)
                } else {
                    Links.data = new Array();
                    Links.data.unshift(d)
                }
                getMovieName("fcookie").setValue("links", Links.data)
            } catch(c) {
                console.log(c)
            }
        }
    }
    if (Setting.get("links").value) {
        linksObj = new Links()
    }
    function Layout() {
        if (Layout.init != undefined) {
            return false
        }
        Layout.panelSize = 330;
        Layout.config = null;
        Layout.panels = new Array();
        Layout.sidebar = "show";
        Layout.init = function () {
            if (!Setting.get("grid").value || currentpage != "home") {
                return false
            }
            if (Setting.get("grid").panel_width != undefined) {
                Layout.panelSize = Setting.get("grid").panel_width
            }
            if ($.cookie("layout_config_" + user) == null) {
                Layout.config = {
                    panels: [],
                    panelSize: Layout.panelSize,
                    sidebar: Layout.sidebar
                };
                $.cookie("layout_config_" + user, JSON.stringify(Layout.config), {
                    expires: 10000000,
                    path: "/"
                })
            } else {
                Layout.config = JSON.parse($.cookie("layout_config_" + user));
                if (Layout.config.panels == undefined) {
                    Layout.config.panels = new Array()
                }
                if (Layout.config.panelSize == undefined) {
                    Layout.config.panelSize = Layout.panelSize
                }
            }
            $("head").append('<style type="text/css">#heading{display:inline-block !important;}body.grid .common-form.standard-form{width:543px; margin:0 auto; padding:10px;}.tabMenu{margin:5px 0 !important;}</style>');
            $("#heading").after(Layout.menu("plus"));
            $("#content").append('<a id="expand_sidebar" class="hide" href="#"><img src="' + srcURL + 'images/action_forward.gif" class="show" /><img src="' + srcURL + 'images/action_back.gif" class="hide" /></a>');
            $(document.body).append('<div id="banana" style="display:none;"><a class="bmenu" href="#menu">Menu</a> | <a class="bsearches" href="#searches">Searches</a> | <a class="blist" href="http://twitter.com/' + user + '/lists/memberships">Lists : ' + $("#lists_count").text() + '</a> | <a class="bfollowing" href="/following">Following : ' + $("#following_count").text() + '</a> | <a class="bfollowers" href="/followers">Followers :' + $("#follower_count").text() + '</a> | <a class="btweets" href="/' + user + '">Tweets : ' + $("#update_count").text() + "</a></div>");
            $("#status_update_box").after('<div id="width_helper"></div>');
            $(document.body).append('<div id="banana_lists" class="banana_menu" style="display:none;"><ul></ul></div>');
            $(".sidebar-menu.lists-links li").each(function () {
                $("#banana_lists ul").append($(this).clone().click(function () {
                    $("#side .sidebar-menu.lists-links " + $(this).find("a").attr("class").replace(/(.+) (.+)/, ".$1.$2")).click();
                    return false
                }))
            });
            $("#banana .blist").mouseover(function () {
                $(".banana_menu").hide();
                $("#banana_lists").show()
            });
            $("#banana_lists").hover(function () {},


            function () {
                $(".banana_menu").hide()
            });
            $(document.body).append('<div id="banana_searches" class="banana_menu" style="display:none;"><ul></ul></div>');
            $(".sidebar-menu.saved-search-links li").each(function () {
                $("#banana_searches ul").append($(this).clone())
            });
            $("#banana_searches li a").isSearchLink(SEARCH_CALLBACKS.savedSearchLink);
            $("#banana .bsearches").mouseover(function () {
                $(".banana_menu").hide();
                $("#banana_searches").show()
            });
            $("#banana_searches").hover(function () {},


            function () {
                $(".banana_menu").hide()
            });
            $(document.body).append('<div id="banana_menu" class="banana_menu" style="display:none;"><ul></ul></div>');
            $("#banana_menu ul").append('			<li id="bhome_tab" class=""><a><span>Home</span></a></li>			<li id="breplies_tab" class=""><a><span>@' + user + '</span></a></li>			<li id="bdirect_messages_tab" class=""><a><span>Direct Messages</span></a></li>			<li id="bfavorites_tab" class=""><a><span>Favorites</span></a></li>			<li id="blinks_tab"><a><span>Links</span></a></li>');
            $("#banana_menu li").click(function () {
                var e = $(this).attr("id");
                switch (e) {
                case "bhome_tab":
                case "breplies_tab":
                case "bdirect_messages_tab":
                case "bfavorites_tab":
                case "blinks_tab":
                    $("#side #" + e.substr(1) + " a:first").click();
                    break
                }
            });
            $("#banana .bmenu").mouseover(function () {
                $(".banana_menu").hide();
                $("#banana_menu").show()
            });
            $("#banana_menu").hover(function () {},


            function () {
                $(".banana_menu").hide()
            });
            $("#banana a.bfollowing, #banana a.bfollowers, #banana a.btweets").mouseover(function () {
                $(".banana_menu").hide()
            });
            $("#expand_sidebar img").click(function () {
                Layout.siebar_visible($(this).hasClass("hide") ? "hide" : "show")
            });
            $(".grid_list.plus li").click(function () {
                switch (this.getAttribute("value")) {
                case "DM":
                    p = new PanelDM();
                    break;
                case "favorites":
                    p = new PanelFavorites();
                    break;
                case "my":
                    p = new PanelMy();
                    break;
                case "@":
                    p = new PanelMention();
                    break;
                case "lists":
                    p = new PanelLists($(this).attr("user"), $(this).attr("slug"));
                    break;
                case "searches":
                    p = new PanelSearches($(this).attr("keyword"));
                    break;
                case "followers":
                    p = new PanelFollow("followers");
                    break;
                case "retweets_by_others":
                case "retweets":
                case "retweeted_of_mine":
                    p = new PanelRetweets(this.getAttribute("value"));
                    break
                }
                Layout.add(p);
                $(this).parent().hide()
            });
            $(".grid_extends").livequery("click", function () {
                this.blur();
                var e = $(this).parent().find(".grid_list");
                e.show();
                e.css("left", ($(this).position().left) + "px").css("top", ($(this).position().top + $(this).height() + 1) + "px");
                return false
            });
            $(".grid_list").livequery("mouseleave", function () {
                $(this).hide()
            });
            $(".grid_list.minus li").livequery("click", function () {
                var f = $(this);
                var e = (/grid_layout_(\d+)/.exec($($(this).parents("td")[0]).attr("id")))[1];
                switch (this.getAttribute("value")) {
                case "DM":
                    Layout.change(e, (new PanelDM()));
                    break;
                case "favorites":
                    Layout.change(e, (new PanelFavorites()));
                    break;
                case "my":
                    Layout.change(e, (new PanelMy()));
                    break;
                case "@":
                    Layout.change(e, (new PanelMention()));
                    break;
                case "close":
                    Layout.remove(e);
                    break;
                case "lists":
                    Layout.change(e, (new PanelLists($(this).attr("user"), $(this).attr("slug"))));
                    break;
                case "retweets_by_others":
                case "retweets":
                case "retweeted_of_mine":
                    Layout.change(e, (new PanelRetweets(this.getAttribute("value"))));
                    break;
                case "searches":
                    Layout.change(e, (new PanelSearches(f.attr("keyword"))));
                    break
                }
                $(this).parent().hide()
            });
            $(".grid_refresh").live("click", function (h) {
                if ($(this).hasClass("plus")) {
                    for (var f = 0; f < Layout.panels.length; f++) {
                        Layout.panels[f].fetch()
                    }
                    var j = $(document.body).attr("id");
                    switch (j) {
                    case "home":
                    case "replies":
                    case "inbox":
                    case "sent":
                    case "favorites":
                    case "retweets_by_others":
                    case "retweets":
                    case "retweeted_of_mine":
                        $("#" + j + "_tab .in-page-link").click();
                        break;
                    case "search":
                        var k = $(".delete-search-link");
                        var g = $(".save-search-link");
                        if (k.length > 0) {
                            $('.search-link[title="' + $(".delete-search-link").attr("title") + '"]').click()
                        } else {
                            $('.search_link[title="' + $(".save-search-link").attr("title") + '"]').click()
                        }
                        break
                    }
                } else {
                    if (Layout.panels.length > 0) {
                        var l = $(this).parents("td");
                        var e = Layout.getIndexById((/grid_layout_(\d+)/.exec($(l[0]).attr("id")))[1]);
                        Layout.panels[e - 1].fetch()
                    }
                }
            });
            $(page).watch("isTimelineChange", function (e, f, h) {
                if (!h) {
                    $(".grid_refresh.plus").removeClass("loading");
                    var g = $("#grid_layout_0 #heading").html();
                    if (g != null) {
                        var g = g.replace(/Tweets mentioning (@\w+)/, "$1");
                        var g = g.replace(/Remove this saved search/, "Remove");
                        var g = g.replace(/Real-time results for <b>(.+?)<\/b>/, "$1");
                        var g = g.replace(/Direct messages sent only to you/, "Direct Messages");
                        var g = g.replace(/Retweets by others/, "by others");
                        var g = g.replace(/Retweets by you/, "by you");
                        var g = g.replace(/Your tweets, retweeted/, "your");
                        $("#grid_layout_0 #heading").html(g);
                        setTimeout('$(".save-search-link").isSaveSearchLink();$(".delete-search-link").isRemoveSearchLink();', 1000)
                    }
                } else {
                    $(".grid_refresh.plus").addClass("loading")
                }
            });
            if (Layout.config.panels.length > 0) {
                Layout.init_repaint();
                for (var b = 0; b < Layout.config.panels.length; b++) {
                    var a = Layout.config.panels[b];
                    if (a == "mention") {
                        p = new PanelMention()
                    } else {
                        if (a == "my") {
                            p = new PanelMy()
                        } else {
                            if (a == "favorites") {
                                p = new PanelFavorites()
                            } else {
                                if (a == "DM") {
                                    p = new PanelDM()
                                } else {
                                    if (a.indexOf("lists") == 0) {
                                        var c = a.match(/lists\/@(.+)\/(.+)/);
                                        p = new PanelLists(c[1], c[2])
                                    } else {
                                        if (a == "follow") {
                                            p = new PanelFollow("followers")
                                        } else {
                                            if (a.indexOf("searches") == 0) {
                                                var c = a.match(/searches\/#(.+)/);
                                                p = new PanelSearches(c[1])
                                            } else {
                                                if (a == "retweets_by_others" || a == "retweets" || a == "retweeted_of_mine") {
                                                    p = new PanelRetweets(a)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Layout._add(p)
                }
            }
            Layout.siebar_visible(Layout.config.sidebar == "hide" ? "hide" : "show");
            var d = $("#retweet_tabs li a span");
            if (Layout.panels.length > 0 && d.length > 0) {
                d.each(function () {
                    $this = $(this);
                    var e = $this.text();
                    var e = e.replace(/Retweets by others/, "by others");
                    var e = e.replace(/Retweets by you/, "by you");
                    var e = e.replace(/Your tweets, retweeted/, "your");
                    $this.text(e)
                })
            }
            $(window).resize(function () {
                Layout.repaint()
            })
        };
        Layout.siebar_visible = function (a) {
            if (a == "hide") {
                $("#expand_sidebar").addClass("show").removeClass("hide");
                $("#side").hide();
                Layout.config.sidebar = "hide";
                $("#banana").show()
            } else {
                $("#expand_sidebar").addClass("hide").removeClass("show");
                $("#side").show();
                Layout.config.sidebar = "show";
                $("#banana").hide()
            }
            $("#expand_sidebar")[0].blur();
            Layout.setConfig();
            Layout.repaint()
        };
        Layout.menu = function (e) {
            var d = '			<a href="#" class="grid_extends ' + e + '" title="' + (e == "minus" ? "Click to remove panel" : "Click to add panels") + '"><img src="' + srcURL + "images/" + e + '.gif" /></a>			<a href="#" class="grid_refresh ' + (e == "minus" ? "loading" : "") + " " + e + '"><img src="' + srcURL + 'images/refresh.gif" /></a>';
            d += '<ul class="grid_list ' + e + '" style="display:none;">				' + (e == "minus" ? '<li value="close">Close</li>' : "") + '				<li value="@">Mention @' + user + '</li>				<li value="DM">Direct Messages</li>				<li value="favorites">Favorites</li>				<li value="my">My Tweets</li>				<li value="retweets_by_others" class="first">Retweets by others</li>				<li value="retweets">Retweets by you</li>				<li value="retweeted_of_mine">Your tweets, retweeted</li>';
            var c = 0;
            $(".sidebar-menu.lists-links li a").each(function () {
                var f = JSON.parse($(this).attr("data"));
                d += '<li value="lists" user="' + f.user + '" mode="' + f.mode + '" slug="' + f.slug + '"' + (c++==0 ? ' class="first"' : "") + ">Lists " + f.full_name + "</li>"
            });
            var c = 0;
            var b = "";
            var a = [];
            $(".sidebar-menu.saved-search-links li a span").each(function () {
                var f = $(this).text();
                b += '<li value="searches" keyword="' + f + '">Search/#' + f + "</li>";
                a.push(f)
            });
            if (a.length > 0) {
                d += '<li value="searches" keyword="_all" class="first">All</li>' + b
            }
            d += "</ul>";
            return d
        };
        Layout.getIndexById = function (b) {
            for (var a = 0; a < $("#grid_layout td").length; a++) {
                if ($("#grid_layout td")[a].id == "grid_layout_" + b) {
                    return a
                }
            }
        };
        Layout.remove = function (b) {
            var a = Layout.getIndexById(b);
            Layout.panels.splice(a - 1, 1);
            $("#grid_layout_" + b).remove();
            Layout.setConfig();
            if ($("#grid_layout td").length == 1) {
                Layout.destory()
            } else {
                Layout.repaint()
            }
        };
        Layout.change = function (d, a) {
            var b = Layout.getIndexById(d);
            Layout.panels.splice(b - 1, 1, a);
            var c = $("#grid_layout_" + d).prev();
            $("#grid_layout_" + d).remove();
            c.after('<td mode="' + a.mode + '" id="grid_layout_' + d + '"></td>');
            a.init($("#grid_layout_" + d));
            $("#grid_layout_" + d + " .grid_timeline_heading h1").after(Layout.menu("minus"));
            Layout.setConfig();
            Layout.repaint()
        };
        Layout.setConfig = function () {
            Layout.config.panels = new Array();
            for (var a = 1; a < $("#grid_layout td").length; a++) {
                Layout.config.panels.push($("#grid_layout td")[a].getAttribute("mode"))
            }
            $.cookie("layout_config_" + user, JSON.stringify(Layout.config), {
                expires: 10000000
            })
        };
        Layout.add = function (a) {
            Layout._add(a);
            Layout.setConfig();
            Layout.repaint()
        };
        Layout._add = function (a) {
            if ($("#grid_layout").length == 0) {
                $("#content .wrapper .section").wrap('<table id="grid_layout"><tr><td id="grid_layout_0"></td></tr></table>');
                $("body").addClass("grid")
            }
            $("#grid_layout tr").append('<td mode="' + a.mode + '" id="grid_layout_' + ($("#grid_layout td").length) + '" class="tdnest"></td>');
            var b = $("#grid_layout td:last");
            a.init(b);
            b.find(".grid_timeline_heading h1").after(Layout.menu("minus"));
            Layout.panels.push(a);
            Layout.repaint()
        };
        Layout.init_repaint = function () {
            var c = $(document.body).width();
            var b = c - ($("#side").css("display") == "none" ? 10 : $("#side").width()) - 40;
            var d = Math.min(400, Math.max(280, Math.ceil(b / (Layout.config.panels.length + 1)) - 20));
            var e = Layout.panelSize;
            Layout.panelSize = d;
            var a = false;
            $(".section").each(function () {
                if ($(this).width() != Layout.panelSize) {
                    a = true;
                    return false
                }
            });
            if (e != Layout.panelSize || a) {
                $("head").append('<style type="text/css" id="panelCss">#heading{display:inline-block !important;}body.grid #grid_layout .section{width:' + Layout.panelSize + "px !important;}body.grid .section .status-body{width:" + (Layout.panelSize - 90) + "px !important;}body.grid .section.my .status-body{width:" + (Layout.panelSize - 20) + "px !important;}</style>")
            }
        };
        Layout.repaint = function () {
            Layout.init_repaint();
            var c = $("#grid_layout td").length > 1 ? $("#grid_layout") : $(".wrapper .section");
            var a = $(".common-form.standard-form");
            if ($("#side").css("display") == "none") {
                var b = 0;
                $("#content").css("-moz-border-radius-topright", "5px")
            } else {
                var b = $("#side").width();
                $("#content").css("-moz-border-radius-topright", "0")
            }
            $("#container").css("width", (Math.min(c.width(), $("#width_helper").width()) + 20 + b + 1) + "px");
            $("#expand_sidebar").css("top", "272px").css("margin-left", ($("#content").width() - 8) + "px");
            $("#banana").css("top", ($(window).height() - $("#banana").height() - 4) + "px").css("left", ($(window).width() / 2 - $("#banana").width() / 2) + "px");
            $("#banana_lists").css("top", ($("#banana").css("top").replace(/(\d+)(px)/, "$1") - $("#banana").height() - $("#banana_lists").height() + 8) + "px");
            var d = Number($("#banana").css("left").replace(/(\d+)(px)/, "$1")) - 2;
            $("#banana_lists").css("left", (d + $(".blist").position().left) + "px");
            $("#banana_searches").css("top", ($("#banana").css("top").replace(/(\d+)(px)/, "$1") - $("#banana").height() - $("#banana_searches").height() + 8) + "px");
            $("#banana_searches").css("left", (d + $(".bsearches").position().left) + "px");
            $("#banana_menu").css("top", ($("#banana").css("top").replace(/(\d+)(px)/, "$1") - $("#banana").height() - $("#banana_menu").height() + 8) + "px");
            $("#banana_menu").css("left", (d + $(".bmenu").position().left) + "px")
        };
        Layout.destory = function () {
            $(".wrapper").append($("#grid_layout td:first .section"));
            $("#grid_layout").remove();
            $("body").removeClass("grid");
            $("#container").css("width", "");
            $(".common-form.standard-form").css("width", "").css("margin", "").css("padding", "");
            Layout.panels = new Array();
            Layout.repaint()
        };
        Layout.init()
    }
    function PanelMention() {
        this.mode = "mention";
        this.id = this.mode + "_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.nest = null;
        this.init = function (b) {
            this.nest = b;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;}</style>");
            var c = '			<div class="section  grid ' + this.mode + ' grid" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/#replies">@' + user + '</a></h1>				</div>					<ol id="timeline" class="statuses ' + this.id + '"></ol>					<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            b.append(c);
            b.obj = this;
            var a = this;
            this.fetch();
            $("#grid_pagination." + this.id).livequery("click", function () {
                a.fetch(a.nextpage, a.max_id);
                return false
            })
        };
        this.fetch = function (a, c) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var b = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            b.open("GET", (a == null || c == null) ? "http://twitter.com/replies" : "http://twitter.com/replies?max_id=" + c + "&page=" + a + "&twttr=true", true);
            b.setRequestHeader("Accept", "application/json, text/javascript, */*");
            b.self = this;
            b.onreadystatechange = function () {
                if (b.readyState == 4 && b.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var d = JSON.parse(this.responseText);
                    if (a == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    var e = d["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = e[1];
                    this.self.nextpage = e[2];
                    $("#grid_pagination." + this.self.id).html('<a rel="next" id="more" href="#" class="round more">more</a>');
                    Reply.refresh($("#timeline." + this.self.id + ".statuses"))
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            b.send(null)
        }
    }
    function PanelMy() {
        this.mode = "my";
        this.id = this.mode + "_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.nest = null;
        this.init = function (b) {
            this.nest = b;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .mine .thumb.vcard.author{display:none;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 30) + "px !important;margin-left:5px !important;}.section." + this.mode + " .actions{width:16px; right:0}</style>");
            var c = '			<div class="section grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/' + user + '">My Tweets</a></h1>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            b.append(c);
            b.obj = this;
            this.fetch();
            var a = this;
            $("#grid_pagination." + this.id).livequery("click", function () {
                a.fetch(a.nextpage, a.max_id);
                return false
            })
        };
        this.fetch = function (a, c) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var b = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            b.open("GET", (a == null || c == null) ? "http://twitter.com/" + user + "?page=1&twttr=true" : "http://twitter.com/" + user + "?max_id=" + c + "&page=" + a + "&twttr=true", true);
            b.setRequestHeader("Accept", "application/json, text/javascript, */*");
            b.self = this;
            b.onreadystatechange = function () {
                if (b.readyState == 4 && b.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var d = JSON.parse(this.responseText);
                    if (a == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    if (d["#pagination"] == null) {
                        $("#grid_pagination." + this.self.id).hide();
                        return
                    }
                    var e = d["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = e[1];
                    this.self.nextpage = e[2];
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" class="round more" href="#">more</a>');
                    Reply.refresh($("#timeline." + this.self.id + ".statuses"))
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            b.send(null)
        }
    }
    function PanelFavorites() {
        this.mode = "favorites";
        this.id = this.mode + "_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.nest = null;
        this.init = function (b) {
            this.nest = b;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;}</style>");
            var c = '			<div class="section grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/#favorites">Favorites</a></h1>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            b.append(c);
            b.obj = this;
            this.fetch();
            var a = this;
            $("#grid_pagination." + this.id).livequery("click", function () {
                a.fetch(a.nextpage, a.max_id);
                return false
            })
        };
        this.fetch = function (a, c) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var b = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            b.open("GET", (a == null || c == null) ? "http://twitter.com/favorites" : "http://twitter.com/favorites?max_id=" + c + "&page=" + a + "&twttr=true", true);
            b.setRequestHeader("Accept", "application/json, text/javascript, */*");
            b.self = this;
            b.onreadystatechange = function () {
                if (b.readyState == 4 && b.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var d = JSON.parse(this.responseText);
                    if (a == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(d["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    if (d["#pagination"] == null) {
                        $("#grid_pagination." + this.self.id).hide();
                        return
                    }
                    var e = d["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = e[1];
                    this.self.nextpage = e[2];
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" class="round more" href="#">more</a>');
                    Reply.refresh($("#timeline." + this.self.id + ".statuses"))
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            b.send(null)
        }
    }
    function PanelDM() {
        this.mode = "DM";
        this.id = this.mode + "_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.method = "inbox";
        this.nest = null;
        this.init = function (b) {
            this.nest = b;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;} #content .section." + this.mode + " .tabMenu li.active a {background-color:#FFFFFF;border-color:#C4C4C4 #C4C4C4 #FFFFFF;border-style:solid;border-width:1px;color:#333333;margin-right:1px;padding:5px 14px;}#content .section." + this.mode + " .tabMenu li.loading a {background-image:url(" + srcURL + "images/spinner.gif);background-position:center center;background-repeat:no-repeat;color:transparent !important}#timeline.statuses." + this.id + "{border-top:solid 1px #ccc!important;}#timeline.statuses." + this.id + " li:first-child{border-top:none;}</style>");
            $("." + this.id + " .replydm a").livequery("click", function () {
                if ($(document.body).attr("id") == "inbox" || $(document.body).attr("id") == "sent") {
                    var d = $(this).attr("title").match(/reply to (\w+)/)[1];
                    $("#direct_message_user_id option").each(function () {
                        if ($(this).text() == d) {
                            $(this).attr("selected", "selected");
                            $("#text")[0].focus();
                            return false
                        }
                    })
                } else {
                    $("#status")[0].value = $(this).attr("title").replace(/reply to (\w+)/, "d @$1 ");
                    $("#status")[0].focus()
                }
                return false
            });
            var c = '			<div class="section grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/#inbox">Direct Messages</a></h1>					<ul class="tabMenu" style="display:block">					  <li id="inbox_tab_' + this.id + '" class="dm_' + this.id + ' active"><a class="in-page-link" href="http://twitter.com/inbox"><span>Inbox</span></a></li>					  <li id="sent_tab_' + this.id + '" class="dm_' + this.id + '"><a class="in-page-link" href="http://twitter.com/sent"><span>Sent</span></a></li>					</ul>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            b.append(c);
            b.obj = this;
            this.fetch();
            var a = this;
            $("#grid_pagination." + this.id).livequery("click", function () {
                a.fetch(a.nextpage, a.max_id);
                return false
            });
            $(".dm_" + this.id).livequery("click", function () {
                var d = ($(this).attr("id").match(/(sent|inbox)/))[1];
                a.fetch(null, null, d);
                return false
            })
        };
        this.fetch = function (a, c, d) {
            this.method = (d == undefined ? this.method : d);
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            $(".dm_" + this.id).removeClass("active");
            $("#" + this.method + "_tab_" + this.id).addClass("active").addClass("loading");
            var b = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            b.open("GET", (a == null || c == null) ? "http://twitter.com/" + this.method : "http://twitter.com/" + this.method + "?max_id=" + c + "&page=" + a + "&twttr=true", true);
            b.setRequestHeader("Accept", "application/json, text/javascript, */*");
            b.self = this;
            b.onreadystatechange = function () {
                if (b.readyState == 4 && b.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var e = JSON.parse(this.responseText);
                    e["#timeline"] = e["#timeline"].replace(/<span class="reply">/g, '<span class="replydm">');
                    if (a == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(e["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(e["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    $(".dm_" + this.self.id).removeClass("loading");
                    if (e["#pagination"] == null) {
                        $("#grid_pagination." + this.self.id).hide();
                        return
                    }
                    var f = e["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = f[1];
                    this.self.nextpage = f[2];
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" class="round more" href="#">more</a>')
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            b.send(null)
        }
    }
    function PanelRetweets(a) {
        this.mode = a;
        this.id = this.mode + "_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.method = a;
        this.nest = null;
        this.init = function (d) {
            this.nest = d;
            var c = "Retweets";
            switch (this.mode) {
            case "retweets_by_others":
                c = "Retweets by others";
                break;
            case "retweets":
                c = "Retweets by you";
                break;
            case "retweeted_of_mine":
                c = "Your tweets, retweeted";
                break
            }
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;} #content .section." + this.mode + " .tabMenu li.active a {background-color:#FFFFFF;border-color:#C4C4C4 #C4C4C4 #FFFFFF;border-style:solid;border-width:1px;color:#333333;margin-right:1px;padding:5px 14px;}#content .section." + this.mode + " .tabMenu li.loading a {background-image:url(" + srcURL + "images/spinner.gif);background-position:center center;background-repeat:no-repeat;color:transparent !important}#timeline.statuses." + this.id + "{border-top:solid 1px #ccc!important;}#timeline.statuses." + this.id + " li:first-child{border-top:none;}</style>");
            var e = '			<div class="section grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/#inbox">' + c + '</a></h1>					<ul class="tabMenu" id="retweet_tabs" style="display:block">					  <li id="retweets_by_others_tab_' + this.id + '" class="retweets_by_others"><a data=\'{"dispatch_action":"retweets_by_others"}\' class="in-page-link" href="http://twitter.com/retweets_by_others"><span>by others</span></a></li>					  <li id="retweets_tab_' + this.id + '" class="retweets"><a data=\'{"dispatch_action":"retweets"}\' class="in-page-link" href="http://twitter.com/retweets"><span>by you</span></a></li>					  <li id="retweeted_of_mine_tab_' + this.id + '" class="retweeted_of_mine"><a data=\'{"dispatch_action":"retweeted_of_mine"}\' class="in-page-link" href="http://twitter.com/retweeted_of_mine"><span>your</span></a></li>					</ul>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            d.append(e);
            d.obj = this;
            this.fetch();
            var b = this;
            $("#grid_pagination." + this.id).livequery("click", function () {
                b.fetch(b.nextpage, b.max_id);
                return false
            });
            $("." + this.id + " .tabMenu li a").livequery("click", function () {
                try {
                    var g = JSON.parse($(this).attr("data")).dispatch_action
                } catch(f) {
                    var g = "retweeted_of_mine_tab_"
                }
                b.fetch(null, null, g);
                return false
            })
        };
        this.fetch = function (b, d, e) {
            this.method = (e == undefined ? this.method : e);
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            $(".dm_" + this.id).removeClass("active");
            $("#" + this.method + "_tab_" + this.id).addClass("active").addClass("loading");
            var c = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            c.open("GET", (b == null || d == null) ? "http://twitter.com/" + this.method : "http://twitter.com/" + this.method + "?max_id=" + d + "&page=" + b + "&twttr=true", true);
            c.setRequestHeader("Accept", "application/json, text/javascript, */*");
            c.self = this;
            c.onreadystatechange = function () {
                if (c.readyState == 4 && c.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var f = JSON.parse(this.responseText);
                    f["#timeline"] = f["#timeline"].replace(/<span class="reply">/g, '<span class="replydm">');
                    if (b == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(f["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(f["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    $("." + this.self.id + " .tabMenu li").removeClass("loading").removeClass("active");
                    $("." + this.self.id + " .tabMenu li." + this.self.method).addClass("active");
                    if (f["#pagination"] == null) {
                        $("#grid_pagination." + this.self.id).hide();
                        return
                    }
                    var g = f["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = g[1];
                    this.self.nextpage = g[2];
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" class="round more" href="#">more</a>')
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            c.send(null)
        }
    }
    function PanelLists(b, a) {
        this.mode = "lists/@" + b + "/" + a;
        this.id = "lists_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.nest = null;
        this.user = b;
        this.slug = a;
        this.init = function (d) {
            this.nest = d;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;}</style>");
            var e = '			<div class="section  grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/' + this.user + "/" + this.slug + '">@' + this.user + "/" + this.slug + '</a></h1>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            d.append(e);
            d.obj = this;
            var c = this;
            this.fetch();
            $("#grid_pagination." + this.id).livequery("click", function () {
                c.fetch(c.nextpage, c.max_id);
                return false
            })
        };
        this.fetch = function (c, e) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var d = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            d.open("GET", (c == null || e == null) ? "http://twitter.com/" + this.user + "/" + this.slug : "http://twitter.com/" + this.user + "/lists/" + this.slug + "?max_id=" + e + "&page=" + c + "&twttr=true", true);
            d.setRequestHeader("Accept", "application/json, text/javascript, */*");
            d.self = this;
            d.onreadystatechange = function () {
                if (d.readyState == 4 && d.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var f = JSON.parse(this.responseText);
                    if (c == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(f["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(f["#timeline"].replace(/(<ol class="statuses" id="timeline">)|(<\/ol>)/g, ""))
                    }
                    if (f["#pagination"] == null) {
                        $("#grid_pagination." + this.self.id).hide();
                        return
                    }
                    var g = f["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                    this.self.max_id = g[1];
                    this.self.nextpage = g[2];
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" href="#" class="round more">more</a>');
                    Reply.refresh($("#timeline." + this.self.id + ".statuses"))
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            d.send(null)
        }
    }
    function PanelSearches(b) {
        this.mode = "searches/#" + b;
        this.id = "searches_" + (Math.floor(10000 * Math.random()));
        this.nextpage = null;
        this.max_id = null;
        this.nest = null;
        this.keyword = b;
        this.keywords = [];
        var a = this;
        if (b == "_all") {
            $(".sidebar-menu.saved-search-links li a span").each(function () {
                a.keywords.push($(this).text())
            })
        } else {
            this.keywords.push(b)
        }
        this.init = function (d) {
            this.nest = d;
            $("head").append('<style type="text/css">.section.' + this.mode + "{width:" + Layout.panelSize + "px !important;} .section." + this.mode + " .status-body{width:" + (Layout.panelSize - 98) + "px !important;}</style>");
            var e = '			<div class="section  grid ' + this.mode + '" style="float:right">				<div class="grid_timeline_heading ' + this.id + '">					<h1 id="heading_' + this.id + '"><a href="http://twitter.com/#search?q=' + this.keyword + '">#' + (this.keywords.length > 1 ? "(" + this.keywords.join(",") + ")" : this.keyword) + '</a></h1>				</div>				<ol id="timeline" class="statuses ' + this.id + '"></ol>				<div id="grid_pagination" class="' + this.id + '"></div>			</div>';
            d.append(e);
            d.obj = this;
            var c = this;
            this.fetch();
            $("#grid_pagination." + this.id).livequery("click", function () {
                c.fetch(c.nextpage);
                return false
            })
        };
        this.fetch = function (e) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var f = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            var c = "";
            var g = [];
            for (var d = 0; d < this.keywords.length; d++) {
                g.push(encodeURIComponent(this.keywords[d]))
            }
            f.open("GET", (e == null) ? "http://twitter.com/search.json?q=" + (g.join("+OR+")) + "&rpp=20&maxId=null" : "http://twitter.com/search.json" + e, true);
            f.self = this;
            f.onreadystatechange = function () {
                if (f.readyState == 4 && f.status == 200) {
                    this.self.nest.find(".grid_refresh").removeClass("loading")[0].blur();
                    var n = JSON.parse(this.responseText);
                    var r = "";
                    for (var l = 0; l < n.results.length; l++) {
                        try {
                            var j = n.results[l];
                            var q = new Date(Date.parse(j.created_at));
                            var h = ["Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                            var k = (q.getHours() % 12) + ":" + q.getMinutes() + " " + (q.getHours() > 12 ? "PM" : "AM") + " " + h[q.getMonth()] + " " + q.getDay() + "th";
                            j.text = j.text.replace(/@(\w+)/g, '<a href="/$1" class="tweet-url username">@$1</a>');
                            j.text = j.text.replace(/#(\w+)/g, '<a class="tweet-url hashtag" title="#$1" href="/search?q=%23$1">#$1</a>');
                            j.text = j.text.replace(/(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig, '<a href="$1" target="_blank">$1</a>');
                            r += '						<li id="status_' + j.id + '" class="hentry status search_result u-' + j.from_user + '">							<span class="thumb vcard author">								<a href="http://twitter.com/' + j.from_user + '" class="tweet-url profile-pic"><img src="' + j.profile_image_url + '"/></a>							</span>							<span class="status-body">								<a href="http://twitter.com/' + j.from_user + '" class="tweet-url screen-name">' + j.from_user + '</a>								<span class="msgtxt en" id="msgtxt' + j.id + '">' + j.text + '</span>							<span class="meta"><a href="http://twitter.com/mitcha/statuses/' + j.id + '">' + k + '</a>							<span class="actions">								<a id="status_star_' + j.id + '" class="fav-action non-fav" href="#">   </a>								<a class="reply" href="/home?status=@' + j.from_user + "%20&amp;in_reply_to_status_id=" + j.id + "&amp;in_reply_to=" + j.from_user + '">   </a>							</span>						</li>'
                        } catch(o) {
                            console.log(o)
                        }
                    }
                    if (e == undefined) {
                        $("#timeline." + this.self.id + ".statuses").html(r)
                    } else {
                        $("#timeline." + this.self.id + ".statuses").append(r)
                    }
                    this.self.nextpage = n.next_page;
                    $("#grid_pagination." + this.self.id).show().html('<a rel="next" id="more" href="#" class="round more">more</a>');
                    Reply.refresh($("#timeline." + this.self.id + ".statuses"))
                }
            };
            this.nest.find(".grid_refresh").addClass("loading");
            f.send(null)
        }
    }
    Layout();
    $(".wrapper .section").prepend('<div id="externalWindow" style="display:none"><iframe id="externalContentHolder" width="100%" height="100%" name="externalContentHolder"/><input class="round-btn" id="externalWindow_close" type="button" value="Close" /></div>');
    $(".status-btn.round-btn.spellcheckSubmit").click(function (b) {
        setTimeout("popup('externalWindow','show')", 1);
        var a = "#status";
        if ($("body").attr("id") == "direct_messages") {
            a = "#direct_message_form #text"
        }
        $("#spellcheck_content").val(($(a).val()));
        $("#spellcheck_form").submit();
        return false
    });
    $("#externalWindow_close").click(function () {
        popup("externalWindow", "hide")
    });
    $(".status-btn.round-btn.tinyTextSubmit").click(function (b) {
        try {
            var a = "#status";
            if ($("body").attr("id") == "direct_messages") {
                a = "#direct_message_form #text"
            }
            $(a).val($(a).val().replace(/([,.?'()])[ ]/gim, "$1"));
            $("#tinyURL")[0].convertTinyURL($(a).val())
        } catch(b) {
            console.log(b);
            return false
        }
        return false
    });
    Reply = function () {
        Reply.countLimit = 10;
        Reply.count = 0;
        Reply.color = [
            ["#ffefef", "#ff9999"],
            ["#f2efff", "#ac99ff"],
            ["#effffb", "#99ffe5"],
            ["#fffdef", "#fff199"],
            ["#fffdef", "#fff199"],
            ["#eff2ff", "#99acff"],
            ["#ffeffe", "#ff99f8"],
            ["#f4efff", "#b999ff"],
            ["#efffef", "#99ff99"],
            ["#effdff", "#99f3ff"],
            ["#fffcef", "#ffde4c"]];
        Reply.init = function () {
            $("a.replyReply").live("click", function () {
                Reply.count = 0;
                $(this)[0].blur();
                return Reply.open($(this), true)
            });
            $(page).watch("isTimelineChange", function (a, b, c) {
                if (!c) {
                    Reply.refresh()
                }
            });
            $("#pagination").live("click", function () {
                setTimeout("Reply.checkNewPage(0)", 500)
            });
            Reply.checkNewPage = function (a) {
                if (a < 100 && $("#pagination #more").hasClass("loading")) {
                    setTimeout("Reply.checkNewPage(" + (++a) + ")", 500)
                } else {
                    Reply.refresh()
                }
            };
            Reply.refresh()
        };
        Reply.open = function (e, g) {
            if (++Reply.count > Reply.countLimit) {
                Reply.count = 0;
                return false
            }
            e.text("loading....");
            var f = $(e.parents("li")[0]);
            var a = f.css("background-color");
            if (a == "transparent" || a == "rgb(247, 247, 247)") {
                var d = Reply.color[Math.floor(Math.random() * (Reply.color.length))];
                f.css("background-color", d[0]);
                f.css("border-left", "solid 2px " + d[1])
            }
            var c = e.attr("href");
            $.ajax({
                url: c,
                type: "GET",
                dataType: "json",
                success: function (q) {
                    var n = n;
                    content = q.text.replace(/@(\w+)/g, '<a href="/$1" class="tweet-url username">@$1</a>');
                    content = content.replace(/#(\w+)/g, '<a class="tweet-url hashtag" title="#$1" href="/search?q=%23$1">#$1</a>');
                    content = content.replace(/(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig, '<a href="$1" target="_blank">$1</a>');
                    var b = ["Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                    var s = new Date(Date.parse(q.created_at));
                    var o = (s.getHours() % 12) + ":" + s.getMinutes() + " " + (s.getHours() > 12 ? "PM" : "AM") + " " + b[s.getMonth()] + " " + s.getDay() + "th";
                    var j = Math.ceil(Math.random() * 900000);
                    var r = '				<li id="status_' + q.id + '" class="hentry u-' + q.user.screen_name + " status replyReply key_" + j + '" style="background-color:' + f.css("background-color") + "; border-left:solid 2px " + f.css("border-left-color") + ';">					<span class="thumb vcard author">						<a class="tweet-url profile-pic url" href="http://twitter.com/' + q.user.screen_name + '">							<img width="48" height="48" src="' + q.user.profile_image_url + '" class="photo fn" alt="' + q.user.name + '"/>						</a>					</span>					<span class="status-body" style="width:' + ($(f[0]).width() - 80) + 'px !important">						<strong>							<a title="' + q.user.name + '" class="tweet-url screen-name" href="http://twitter.com/' + q.user.screen_name + '">' + q.user.screen_name + '</a>						</strong>					<span class="actions">						<div>							<a title="favorite this tweet" id="status_star_' + q.id + '" class="fav-action non-fav">  </a>						</div>					</span>					<span class="entry-content">' + content + '</span>						<span class="meta entry-meta">							<a rel="bookmark" class="entry-date" href="http://twitter.com/' + q.user.screen_name + "/status/" + q.id + '">								<span data="{time:\'' + q.created_at + '\'}" class="published timestamp">' + o + "</span>							</a>						" + (q.in_reply_to_screen_name == null || q.in_reply_to_status_id == null ? "" : ('<a class="replyReply" href="http://twitter.com/' + q.in_reply_to_screen_name + "/status/" + q.in_reply_to_status_id + '">in reply to ' + q.in_reply_to_screen_name + "</a>")) + '					</span>						<ul class="actions-hover">							<li>								<span class="reply">									<span class="reply-icon icon"/>									<a title="reply to ' + q.user.screen_name + '" href="/?status=@' + q.user.screen_name + "%20&amp;in_reply_to_status_id=" + q.id + "&amp;in_reply_to=" + q.user.screen_name + '">Reply</a>								</span>							</li>							' + (q.user.screen_name == user ? '<li><span class="del"><span class="delete-icon icon"/><a title="delete this tweet" href="#">Delete</a></span></li>' : "") + "						</ul>					</span>				</li>";
                    e.hide();
                    f.after(r);
                    if (g) {
                        var h = $(".key_" + j + " a.replyReply");
                        if (h.length > 0) {
                            Reply.open(h, true)
                        }
                    }
                }
            });
            return false
        };
        Reply.refresh = function (a) {
            if ($(document.body).hasClass("pbtweet")) {
                return false
            }
            var b = a ? a.find(".meta.entry-meta") : $(".meta.entry-meta");
            b.each(function () {
                var c = $(this).find("a");
                for (var e = 0; e < c.length; e++) {
                    var d = $(c[e]).text();
                    if (d.indexOf("in reply to") > -1 || d.indexOf("宛") > -1 || d.indexOf("en respuesta a") > -1) {
                        $(c[e]).addClass("replyReply")
                    }
                }
            })
        };
        Reply.init()
    };
    Reply();
    Profile = function () {
        Profile.delayOver;
        Profile.delayOut;
        Profile.user;
        Profile.userId;
        Profile.history;
        Profile.historyCurrent;
        (function () {
            $(document.body).append('			<div id="profile_box" style="display:none;" class="round">				<div id="profile_control">					<table>						<tr>							<td class="leftnest back">								<div>									<img src="' + srcURL + 'images/back.gif" class="icon"/>									<img src="" height="19" class="profileImg"><span class="screenname"></span>								</div>							</td>							<td class="close">								<img src="' + srcURL + 'images/close.gif"/>							</td>						</tr>					</table>				</div>				<div id="profile_content">					<table class="info_layout">						<tr>							<td class="profile_box_img_nest">								<img id="profile_box_img" width="100%"/>							</td>							<td class="info">								<div id="profile_info"></div>							</td>						</tr>						<tr>							<td colspan="2" class="controls">								<button title="follow" class="btn" id="profile_following_btn" style="display:none">Follow</button>								<button title="direct message" class="btn" id="profile_direct_message_btn">Direct Message</button>								<button title="reply" class="btn" id="profile_reply_btn">@Mention</button>								<button title="protected" class="btn" id="profile_protected_btn" style="display:none"></button>							</td>						</tr>					</table>					<div id="profile_recentTweet"></div>				</div>			</div>');
            $("#following .photo.fn,.thumb.vcard.author .photo.fn,.conv_chain .icons img,.tweet-url.profile-pic img, .tweet-url.hashtag, .side_thumb.photo.fn").livequery("mouseover", function () {
                return;
                try {
                    var self = $(this);
                    var rex = new RegExp("(^/|http://twitter.com/)(.+)");
                    var targetUsername = (self.parent().attr("href").match(rex)[2]);
                    clearTimeout(Profile.delayOver);
                    Profile.delayOver = setTimeout("Profile.open('" + targetUsername + "');", 500)
                } catch(e) {}
            }).livequery("mouseout", function () {
                clearTimeout(Profile.delayOver)
            });
            $(".in-page-link, #profile_recentTweet .reply, #profile_control .close").livequery("click", function (e) {
                Profile.close()
            });
            $("#profile_following_btn").click(function () {
                switch ($(this).attr("relation")) {
                case "friend":
                    Friends.destory(Profile.user, function () {
                        Profile.setRelation("follower");
                        $("#home_tab .in-page-link").click()
                    });
                    break;
                case "following":
                    Friends.destory(Profile.user, function () {
                        Profile.setRelation("none");
                        $("#home_tab .in-page-link").click()
                    });
                    break;
                case "follower":
                    Friends.create(Profile.user, function () {
                        Profile.setRelation("friend");
                        $("#home_tab .in-page-link").click()
                    });
                    break;
                case "none":
                    Friends.create(Profile.user, function () {
                        Profile.setRelation("following");
                        $("#home_tab .in-page-link").click()
                    });
                    break
                }
            });
            $("#profile_direct_message_btn").click(function () {
                switch ($(this).attr("relation")) {
                case "friend":
                case "following":
                case "follower":
                    location.href = "/direct_messages/create/" + Profile.user;
                    break;
                case "none":
                    (new InfoNotification()).setMessage(_("Sorry, you can't send direct message.")).show();
                    break
                }
            });
            $("#profile_reply_btn").click(function () {
                location.href = "http://twitter.com/?status=" + escape("@" + Profile.user)
            });
            $("#profile_control .leftnest.back div").click(function () {
                Profile.back()
            });
            $("#profile_protected_btn").click(function () {
                $("#request_protect_form").submit()
            });
            switch (currentpage) {
            case "following":
            case "followers":
                $("td.thumb.vcard img").mouseover(function () {
                    var rex = new RegExp("(^/|http://twitter.com/)(.+)");
                    var targetUsername = ($(this).parent().attr("href").match(rex)[2]);
                    clearTimeout(Profile.delayOver);
                    Profile.delayOver = setTimeout("Profile.open('" + targetUsername + "');", 500)
                }).mouseout(function () {
                    clearTimeout(Profile.delayOver)
                });
                break
            }
            $(window).resize(function () {
                Profile.profileRepaint()
            });
            Profile.history = new Array();
            Profile.historyCurrent = 0
        })();
        Profile.set = function (targetUsername) {
            $("#profile_box").show();
            $("#profile_box_img").attr("src", "");
            Profile.user = targetUsername;
            document.getElementById("profile_recentTweet").innerHTML = "";
            Profile.loadProfileRecentTweet(targetUsername, 1, null);
            Profile.loadProfileInfo(targetUsername);
            $("#profile_direct_message_btn").hide();
            $("#profile_protected_btn").hide();
            $("#profile_following_btn").hide();
            if (targetUsername != user) {
                Friends.getRelation(targetUsername, function (relation) {
                    Profile.setRelation(relation)
                })
            }
            Profile.profileRepaint();
            if (Profile.historyCurrent < 1) {
                $("#profile_control .leftnest.back div").hide()
            } else {
                $("#profile_control .leftnest.back div").show()
            }
        };
        Profile.open = function (targetUsername) {
            Profile.history.push({
                screenName: targetUsername,
                pic: null
            });
            Profile.historyCurrent = Profile.history.length - 1;
            if (Profile.historyCurrent > 0) {
                var o = Profile.history[Profile.historyCurrent - 1];
                $("#profile_control .leftnest.back .screenname").text("@" + o.screenName.substring(0, 6));
                if (o.pic != null) {
                    $("#profile_control .leftnest.back .profileImg").attr("src", o.pic)
                }
            }
            Profile.set(targetUsername);
            $("#mask").addClass("show").click(Profile.close)
        };
        Profile.back = function () {
            Profile.historyCurrent--;
            if (Profile.historyCurrent > 0) {
                var o = Profile.history[Profile.historyCurrent - 1];
                $("#profile_control .leftnest.back .screenname").text(o.screenName.substring(0, 6));
                if (o.pic != null) {
                    $("#profile_control .leftnest.back .profileImg").attr("src", o.pic)
                }
            }
            Profile.set(Profile.history[Profile.historyCurrent].screenName)
        };
        Profile.close = function () {
            Profile.user = null;
            document.getElementById("profile_recentTweet").innerHTML = "";
            $("#profile_following_btn").hide();
            $("#profile_box_img").attr("src", "");
            $("#profile_info").text("");
            $("#profile_box").hide();
            $("#profile_following_btn").hide();
            $("#profile_direct_message_btn").hide();
            $("#mask").removeClass("show").unbind("click", Profile.close)
        };
        Profile.setRelation = function (relation) {
            switch (relation) {
            case "protected":
            case "requested_protected":
                $("#profile_direct_message_btn").hide();
                $("#profile_following_btn").hide();
                $("#profile_box_img").attr("src", "http://a3.twimg.com/a/1254440757/images/padlock_large.gif");
                $("#profile_protected_btn").show();
                break;
            default:
                $("#profile_direct_message_btn").show();
                $("#profile_following_btn").show();
                $("#profile_following_btn").attr("relation", relation);
                $("#profile_direct_message_btn").attr("relation", relation)
            }
            switch (relation) {
            case "friend":
            case "following":
                $("#profile_following_btn").text("Unfollow");
                break;
            case "follower":
            case "none":
                $("#profile_following_btn").text("Follow");
                break;
            case "protected":
                $("#profile_protected_btn").html("Send following request");
                $("#profile_recentTweet").html('This person has protected their tweets. You need to send a request before you can start following this person.<form method="post" id="request_protect_form" action="/friendships/create/' + Profile.user + '"><input type="hidden" value="' + twttr.form_authenticity_token + '" name="authenticity_token"/></form>');
                break;
            case "requested_protected":
                $("#profile_protected_btn").html("Go cancel following request");
                $("#profile_recentTweet").html('Request sent. You\'ve sent a request to follow this person. If your request is approved, you\'ll be able to view their tweets.<form method="GET" id="request_protect_form" action="' + Profile.user + '"></form>');
                break;
            case "nothing":
                break
            }
        };
        Profile.loadProfileRecentTweet = function (targetUsername, page, max_id) {
            if (Profile.loadProfileRecentTweet.comm != undefined) {
                Profile.loadProfileRecentTweet.comm.abort()
            }
            if (max_id != undefined) {
                max_aux = "&max_id=" + max_id
            } else {
                max_aux = ""
            }
            $("#paginationProfile .round.more").addClass("loading").text("");
            Profile.loadProfileRecentTweet.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/" + targetUsername + "?page=" + page + "&twttr=true" + max_aux,
                dataType: "json",
                success: function (json) {
                    try {
                        $("#paginationProfile .round.more").removeClass("loading");
                        var result = eval(json);
                        if (result["#pagination"] != null) {
                            var p = result["#pagination"].match(/max_id=(\d+)&amp;page=(\d{1,})/);
                            var max_id = p[1];
                            var nextpage = p[2];
                            var button = '<div id="paginationProfile" onclick="Profile.loadProfileRecentTweet(\'' + targetUsername + "'," + nextpage + "," + max_id + ')"><div class="round more">more</div></div>';
                            $("#paginationProfile").remove()
                        } else {
                            button = ""
                        }
                        document.getElementById("profile_recentTweet").innerHTML += result["#timeline"] + button;
                        Reply.refresh($("#profile_recentTweet"))
                    } catch(e) {
                        console.log(e)
                    }
                }
            })
        };
        Profile.loadProfileInfo = function (targetUsername) {
            if (Profile.loadProfileInfo.comm != undefined) {
                Profile.loadProfileInfo.comm.abort()
            }
            Profile.loadProfileInfo.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/users/show.json?screen_name=" + targetUsername,
                dataType: "json",
                success: function (json) {
                    try {
                        var result = eval(json);
                        var img = result.profile_image_url.replace(/(_normal|_mini)/, "");
                        $("#profile_box_img").attr("src", img);
                        for (var i = 0; i < Profile.history.length; i++) {
                            var o = Profile.history[i];
                            if (o.screenName == targetUsername) {
                                if (o.pic == null) {
                                    Profile.history[i].pic = img.replace(/(\.)(JPG|GIF|PNG|jpg|gif|png|jpeg|JPEG)$/, "_mini.$2")
                                }
                            }
                        }
                        Profile.userId = result.id;
                        var _url = result.url == null ? "" : '<li><span class="label">Web : </span> <a target="_blank" href="' + result.url + '">' + result.url + "</a></li>";
                        var _location = (result.location == null || $.trim(result.location) == "") ? "" : '<li><span class="label">Location : </span> <span class="adr">' + result.location + "</span></li>";
                        var _description = (result.description == null || $.trim(result.description) == "") ? "" : '<li><span class="label">Bio : </span>' + result.description + "</li>";
                        var str = '							<ul class="about vcard entry-author">								<li><span class="label">Name : </span><span class="fn"><a target="_blank" href="' + t + result.screen_name + '">' + result.name + "</a></span></li>								" + _location + "								" + _url + "								" + _description + '							</ul>							<table class="score">								<tr><td><span class="label">following </span></td><td><a target="_blank" href="' + t + result.screen_name + '/following"> : ' + result.friends_count + '</a></td></tr>								<tr><td><span class="label">followers </span></td><td><a target="_blank" class="url" href="' + t + result.screen_name + '/followers"> : ' + result.followers_count + '</a></td></tr>								<tr><td><span class="label">tweets </span></td><td><a target="_blank" class="url" href="' + t + result.screen_name + '"> : ' + result.statuses_count + "</a></td></tr>							</table>						";
                        $("#profile_info").html(str)
                    } catch(e) {
                        console.log(e)
                    }
                }
            })
        };
        Profile.profileRepaint = function () {
            try {
                $("#profile_box").css("left", ($(window).width() / 2 - $("#profile_box").width() / 2) + "px").css("height", ($(window).height() - 40) + "px").css("top", (10) + "px");
                $("#profile_content").css("height", ($("#profile_box").height() - $("#profile_control").height()) + "px")
            } catch(e) {
                console.log(e)
            }
        }
    };
    if (Setting.get("profile").value) {
        Profile()
    }
    $("#update-submit").click(function () {
        popup("externalWindow", "hide")
    });
    $("#sign_out_link").click(function (a) {
        $.cookie(user + "_twitpic_auth", null)
    });

    function ToolTip() {
        ToolTip.delayOver;
        ToolTip.delayOut;
        ToolTip.parent;
        ToolTip.id;
        ToolTip.context;
        (function () {
            $(document.body).append('			<div id="tooltip">				<table>					<tr><td class="photo"></td><td class="detail"></td></tr>				</table>				' + (!Setting.get("profile").value ? "" : '<button title="detail" class="btn" id="tooltip_show_profile">Show Detail</button>') + "			</div>			");
            $(".photo.fn,.thumb.vcard.author .photo.fn,.conv_chain .icons img,.tweet-url.profile-pic img, .tweet-url.hashtag, .side_thumb.photo.fn, .users-lists .author img").livequery("mouseover", function () {
                try {
                    ToolTip.context = false;
                    var self = $(this);
                    var rex = new RegExp("(^/|http://twitter.com/)(.+)");
                    var id = (self.parent().attr("href").match(rex)[2]);
                    var top = self.offset().top;
                    if (self.hasClass(".photo") && self.hasClass(".fn")) {
                        top = top - 10
                    }
                    clearTimeout(ToolTip.delayOver);
                    ToolTip.delayOver = setTimeout('ToolTip.open("' + id + '",' + Math.ceil(top) + "," + Math.ceil(self.offset().left + self.width() + 5) + ")", window.profileRunInterval)
                } catch(e) {
                    console.log(e)
                }
            }).livequery("mouseout", function () {
                clearTimeout(ToolTip.delayOver);
                clearTimeout(ToolTip.delayOut);
                ToolTip.delayOut = setTimeout("ToolTip.close()", 500)
            });
            $(".tweet-url.username").livequery("mouseover", function (event) {
                ToolTip.context = true;
                clearTimeout(ToolTip.delayOver);
                var o = $(this).offset();
                var p = $(this).parents("li");
                if (!p.length) {
                    var top = o.top
                } else {
                    var top = p.offset().top
                }
                top = top - $("#tooltip").height() - 10;
                var href = $(this).attr("href");
                var id = href.substr(href.lastIndexOf("/") + 1);
                var left = event.clientX - $("#tooltip").width() / 2;
                ToolTip.delayOver = setTimeout('ToolTip.open("' + id + '",' + Math.ceil(top) + "," + Math.ceil(left) + ")", window.profileRunInterval)
            });
            $(".tweet-url.username").livequery("mouseout", function () {
                clearTimeout(ToolTip.delayOver);
                clearTimeout(ToolTip.delayOut);
                ToolTip.delayOut = setTimeout("ToolTip.close()", 500)
            });
            $("#tooltip").hover(function () {
                clearTimeout(ToolTip.delayOut)
            },


            function () {
                ToolTip.close()
            });
            $("#tooltip_show_profile").click(function () {
                ToolTip.showProfile(ToolTip.id)
            })
        })();
        ToolTip.open = function (id, top, left) {
            left = Math.min($(document.body).width() - $("#tooltip").width() - 10, left);
            $("#tooltip").css("top", Math.max(0, top) + "px").css("left", left + "px");
            $("#tooltip").addClass("loading");
            ToolTip.id = id;
            $("#tooltip_show_profile").css("margin-top", "69px");
            $("#tooltip").show();
            ToolTip.loadProfileInfo(id)
        };
        ToolTip.close = function (id, top, left) {
            $("#tooltip .photo").text("");
            $("#tooltip .detail").text("");
            setTimeout("$('#tooltip').hide();", 50)
        };
        ToolTip.loadProfileInfo = function (targetUsername) {
            if (ToolTip.comm != undefined) {
                ToolTip.comm.abort()
            }
            ToolTip.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/users/show.json?screen_name=" + targetUsername,
                dataType: "json",
                success: function (json) {
                    try {
                        var result = eval(json);
                        console.log(result);
                        var _url = result.url == null ? "" : '<li><span class="label">Web : </span> <a target="_blank" href="' + result.url + '">' + result.url + "</a></li>";
                        var _location = result.location == null ? "" : '<li><span class="label">Location : </span> <span class="adr">' + result.location + "</span></li>";
                        var _description = result.description == null ? "" : '<li><span class="label">Bio : </span>' + result.description + "</li>";
                        var str = '							<ul class="about vcard entry-author">								<li><span class="label">Name : </span><span class="fn">' + result.name + "</span></li>								" + _location + "								" + _url + "								" + _description + '							</ul>							<table>								<tr><td><span class="label">following </span></td><td> : <a target="_blank" href="' + t + result.screen_name + '/following">' + result.friends_count + '</a></td></tr>								<tr><td><span class="label">followers </span></td><td> : <a target="_blank" class="url" href="' + t + result.screen_name + '/followers">' + result.followers_count + '</a></td></tr>								<tr><td><span class="label">tweets </span></td><td> : <a target="_blank" class="url" href="' + t + result.screen_name + '">' + result.statuses_count + "</a></td></tr>							</table>						";
                        $("#tooltip_show_profile").css("margin-top", "0");
                        $("#tooltip .photo").html('<img width="48" height="48" src="' + result.profile_image_url + '" />');
                        var _h = $("#tooltip").height();
                        $("#tooltip .detail").html(str);
                        if (ToolTip.context) {
                            var top = $("#tooltip").position().top - ($("#tooltip").height() - _h) + 4;
                            $("#tooltip").css("top", Math.max(0, top) + "px")
                        }
                        $("#tooltip").removeClass("loading")
                    } catch(e) {
                        console.log(e)
                    }
                }
            })
        };
        ToolTip.showProfile = function (targetUsername) {
            Profile.open(targetUsername);
            $("#tooltip").hide()
        }
    }
    if (Setting.get("tooltip").value == "1") {
        tooltipObj = new ToolTip()
    }
    Follower = function () {
        Follower.name = "tfollowers";
        Follower.mode = 1;
        Follower.lists = new Array();
        Follower.init = function () {
            var a = false;
            $(".sidebar-menu.lists-links a").each(function () {
                var b = JSON.parse($(this).attr("data"));
                if (b.user == user && b.slug == Follower.name && currentpage == "home") {
                    a = true;
                    return false
                }
            });
            if (!a) {
                console.log("make")
            }
        };
        Follower.sync = function (a) {
            if (Follower.comm != undefined) {
                Follower.comm.abort()
            }
            Follower.comm = $.ajax({
                type: "GET",
                url: "http://twitter.com/followers/ids.xml?screen_name=" + user,
                dataType: "xml",
                success: function (c) {
                    var b = c.firstChild.childNodes;
                    Follower.lists = new Array();
                    for (var d = 0; d < b.length; d++) {
                        if (b[d].nodeType != 1) {
                            continue
                        }
                        Follower.lists.push(b[d].firstChild.data)
                    }
                }
            })
        };
        Follower.init()
    };
    Shortcut = function () {
        Shortcut.show;
        Shortcut.init = function () {
            if (Setting.get("shortcut").value != undefined && !Setting.get("shortcut").value) {
                return true
            }
            if ($.cookie("shortcut_" + user) == null || $.cookie("shortcut_" + user) == "0") {
                $.cookie("shortcut_" + user, "0", {
                    expires: 10000000,
                    path: "/"
                });
                Shortcut.show = false
            } else {
                $.cookie("shortcut_" + user, "1", {
                    expires: 10000000,
                    path: "/"
                });
                Shortcut.show = true
            }
            $("div#following").after('			<div class="collapsible' + (Shortcut.show ? "" : " collapsed") + '" id="shortcut_help">				<hr/><h2 id="ss_menu" class="sidebar-title"><span>Keyboard Shortcuts</span></h2>				<div id="shortcut" style="' + (Shortcut.show ? "" : "display:none;") + '">					<table>						<tr><td class="key">?</td><td class="label">Shortcut Help</td></tr>						<tr><td class="key">i</td><td class="label">Edit Mode</td></tr>						<tr><td class="key">ESC</td><td class="label">Command Mode</td></tr>						<tr><td class="key">Alt+Enter</td><td class="label">Submit</td></tr>						<tr><td class="key">J</td><td class="label">Newer Tweet</td></tr>						<tr><td class="key">K</td><td class="label">Older Tweet</td></tr>						<tr><td class="key">D</td><td class="label">Delete Tweet</td></tr>						<tr><td class="key">R</td><td class="label">Reply All</td></tr>						<tr><td class="key">r</td><td class="label">Reply</td></tr>						<tr><td class="key">F</td><td class="label">Favorite</td></tr>						<tr><td class="key">tt</td><td class="label">RT</td></tr>						<tr><td class="key">a</td><td class="label">Refresh all panel</td></tr>						<tr><td class="key">h</td><td class="label">Move Home</td></tr>						<tr><td class="key">2</td><td class="label">Move @' + user + '</td></tr>						<tr><td class="key">d</td><td class="label">Move DM</td></tr>						<tr><td class="key">f</td><td class="label">Move Favorites</td></tr>						<tr><td class="key">8</td><td class="label">Focus search</td></tr>						<tr><td class="key">gg</td><td class="label">Top</td></tr>						<tr><td class="key">G</td><td class="label">Bottom</td></tr>					</table>				</div>			</div>');
            $("#shortcut_help #ss_menu").live("click", function () {
                Shortcut.toggle()
            });
            $(window).ready(function () {
                window.prevScrollTop = 0;
                window.currentPosition = $("#timeline li:eq(0)")
            }).keyup(function (g) {
                var c = g.keyCode;
                var b = String.fromCharCode(g.keyCode);
                window.currentPage = $("body").attr("id");
                if ((Setting.get("suggest").value == "1" && sugg.isActive()) || $("#status_login_box").hasClass("show")) {
                    return
                }
                switch (g.originalEvent.target.type) {
                case "textarea":
                case "text":
                    switch (c) {
                    case 27:
                        $("textarea").blur();
                        $("input").blur();
                        window.scrollTo(0, window.prevScrollTop);
                        break;
                    case 13:
                        if (g.altKey) {
                            switch (currentPage) {
                            case "inbox":
                            case "sent":
                                $("#dm-submit").submit();
                                break;
                            default:
                                $("#status_update_form").submit();
                                break
                            }
                        }
                        break
                    }
                    return;
                default:
                    switch (c) {
                    case 27:
                        Profile.close();
                        break;
                    case 73:
                        window.prevScrollTop = $(window).scrollTop();
                        switch (currentPage) {
                        case "inbox":
                        case "sent":
                            $(".info #text").focus();
                            break;
                        default:
                            $(".info #status").focus();
                            break
                        }
                        return;
                    case 59:
                        if (g.shiftKey) {
                            $("#command_view").show();
                            $("#command_view").text(":")
                        }
                        break;
                    case 191:
                        if (g.shiftKey) {
                            Shortcut.toggle()
                        }
                        break
                    }
                    switch (b) {
                    case "A":
                        $(".grid_refresh.plus").click();
                        break;
                    case "G":
                        if (g.shiftKey) {
                            scrollTo(0, $(document).height())
                        } else {
                            if (prevPressKeyEvent.keyCode == 71 && !prevPressKeyEvent.shiftKey && !g.shiftKey) {
                                scrollTo(0, 0)
                            }
                        }
                        break;
                    case "L":
                        if (window.currentPosition.length != 1) {
                            break
                        }
                        Profile.open(window.currentPosition.find(".tweet-url.screen-name").text());
                        break;
                    case "H":
                        if (!$("#home_tab .in-page-link").length) {
                            location.href = "http://twitter.com/"
                        }
                        $("#home_tab .in-page-link").click();
                        break;
                    case "F":
                        if (!g.shiftKey) {
                            $("#favorites_tab .in-page-link").click()
                        } else {
                            var f = currentPosition.find(".fav-action");
                            if (f.length > 0) {
                                f.click()
                            }
                        }
                        break;
                    case "D":
                        if (!g.shiftKey) {
                            $($("#direct_messages_tab .in-page-link")[0]).click()
                        } else {
                            var h = currentPosition.find(".del");
                            if (h.length > 0) {
                                h.click()
                            }
                            currentPosition = currentPosition.next()
                        }
                        break;
                    case "2":
                        if (!g.ctrlKey) {
                            $("#replies_tab .in-page-link").click()
                        }
                        break;
                    case "8":
                        $("#sidebar_search_q").focus();
                        break;
                    case "R":
                        if (g.shiftKey) {
                            var e = currentPosition.find(".tweet-url.screen-name").text();
                            var a = currentPosition.find(".entry-content").text().match(/@\w+/g);
                            a = (a == null ? [] : a);
                            a.unshift("@" + e);
                            $("#status").val(a.unique().join(" ") + " ").focus();
                            if ((target = currentPosition.attr("id").match(/status_(\d+)/)[1]) > 0) {
                                $("#in_reply_to_status_id").val(target);
                                $("#in_reply_to").val(e)
                            }
                        } else {
                            window.prevScrollTop = $(window).scrollTop();
                            var f = currentPosition.find(".reply");
                            if (f.length > 0) {
                                f.click()
                            }
                        }
                        break;
                    case "T":
                        window.prevScrollTop = $(window).scrollTop();
                        if (prevPressKeyEvent.keyCode == 84 && !prevPressKeyEvent.shiftKey && !g.shiftKey) {
                            $("#status").focus();
                            _screenname = currentPosition.find(".screen-name").text();
                            if (_screenname == "" && $("#profile_box").css("display") == "block") {
                                _screenname = Profile.user;
                                Profile.close()
                            }
                            $("#status").val("RT @" + _screenname + " " + currentPosition.find(".entry-content").text())
                        }
                        break
                    }
                    prevPressKeyEvent = g;
                    break
                }
            }).keydown(function (e) {
                var d = e.keyCode;
                var a = String.fromCharCode(e.keyCode);
                if ((Setting.get("suggest").value && sugg.isActive()) || $("#status_login_box").hasClass("show")) {
                    return
                }
                if ($(e.originalEvent.target).attr("id") == "status") {} else {
                    switch (e.originalEvent.target.type) {
                    case "textarea":
                    case "text":
                        return
                    }
                    switch (a) {
                    case "J":
                    case "K":
                        if (currentPosition.length == 0 || currentPosition[0].clientHeight == 0) {
                            window.currentPosition = $("#timeline li:eq(0)")
                        }
                        var f = a == "J" ? currentPosition.next() : currentPosition.prev();
                        if (f.length == 1) {
                            $(".hentry").removeClass("focus");
                            f.addClass("focus");
                            currentPosition = f;
                            var b = $(window).scrollTop();
                            scrollTo(0, currentPosition.position().top);
                            if (b == $(window).scrollTop() && !$("#pagination a").hasClass("loading")) {
                                $("#pagination a").click()
                            }
                        } else {
                            if (a == "J" && !$("#pagination a").hasClass("loading")) {
                                $("#pagination a").click()
                            }
                        }
                        break
                    }
                }
            })
        };
        Shortcut.init();
        Shortcut.toggle = function () {
            if ($("#shortcut_help").hasClass("collapsed")) {
                $("#shortcut_help").removeClass("collapsed");
                $("#shortcut").show();
                $.cookie("shortcut_" + user, "1", {
                    expires: 10000000,
                    path: "/"
                });
                Shortcut.show = true
            } else {
                $("#shortcut_help").addClass("collapsed");
                $("#shortcut").hide();
                $.cookie("shortcut_" + user, "0", {
                    expires: 10000000,
                    path: "/"
                });
                Shortcut.show = false
            }
        }
    };
    Shortcut();

    function FollowerList() {
        if (pageuser != undefined && user != pageuser) {
            return
        }
        FollowerList.show;
        FollowerList.isLoading = false;
        $("#FollowerList #fss_menu").live("click", function () {
            FollowerList.toggle()
        });
        FollowerList.open = function () {
            $("#FollowerList").removeClass("collapsed");
            $("#FollowerList #sidebar-menu").show();
            $("#followers_view_all").show();
            if (!FollowerList.isLoading) {
                FollowerList.fetch()
            }
            $.cookie("FollowerList_" + user, "1", {
                expires: 10000000,
                path: "/"
            });
            FollowerList.show = true
        };
        FollowerList.close = function () {
            $("#FollowerList").addClass("collapsed");
            $("#FollowerList #sidebar-menu").hide();
            $("#followers_view_all").hide();
            $.cookie("FollowerList_" + user, "0", {
                expires: 10000000,
                path: "/"
            });
            FollowerList.show = false
        };
        FollowerList.toggle = function () {
            if ($("#FollowerList").hasClass("collapsed")) {
                FollowerList.open()
            } else {
                FollowerList.close()
            }
        };
        FollowerList.fetch = function (a) {
            $("#grid_pagination." + this.id + " a.round.more").addClass("loading").html("");
            var b = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
            b.open("GET", (a == null || max_id == null) ? "http://twitter.com/followers" : "://twitter.com/followers?page=" + a, true);
            b.setRequestHeader("Accept", "application/json, text/javascript, */*");
            b.self = this;
            $("#FollowerList").addClass("loading");
            FollowerList.isLoading = true;
            b.onreadystatechange = function () {
                if (b.readyState == 4 && b.status == 200) {
                    var d = this.responseText;
                    var h = d.match(/(<tr id="user_[\s\S]+?<\/tr>)/mg);
                    for (var c = 0; c < h.length; c++) {
                        try {
                            var j = h[c];
                            var g = fullname = img = "";
                            try {
                                g = j.match(/<a href="https?:\/\/twitter.com\/(.+?)".+?title="(.+?)">/)
                            } catch(j) {}
                            try {
                                fullname = j.match(/<span class="label fullname">(.+?)<\/span>/)
                            } catch(j) {}
                            try {
                                img = j.match(/<img .+?src="(.+?)"/)
                            } catch(j) {}
                            var f = '<span class="vcard"><a title="' + fullname[1] + '" rel="contact" hreflang="en" class="url" href="/' + g[1] + '"><img width="24" height="24" src="' + img[1].replace(/normal/, "mini") + '" class="photo fn" alt="' + fullname[1] + '"/></a></span>';
                            $("#followers_list").append(f)
                        } catch(j) {
                            console.log(j)
                        }
                    }
                    $("#FollowerList").removeClass("loading")
                }
            };
            b.send(null)
        };
        this.init = function (a) {
            if ($.cookie("FollowerList_" + user) == null || $.cookie("FollowerList_" + user) == "0") {
                $.cookie("FollowerList_" + user, "0", {
                    expires: 10000000,
                    path: "/"
                });
                FollowerList.show = false
            } else {
                $.cookie("FollowerList_" + user, "1", {
                    expires: 10000000,
                    path: "/"
                });
                FollowerList.show = true
            }
            $("div#following").after('			<hr/><div class="collapsible' + (FollowerList.show ? "" : " collapsed") + '" id="FollowerList">				<h2 id="fss_menu" class="sidebar-title"><span>Followers</span></h2>				<div class="sidebar-menu">					<div id="followers_list"></div>					<div id="followers_view_all" style="display:none"><a rel="me" href="http://twitter.com/followers">View all…</a></div>				</div>			</div>');
            if (FollowerList.show) {
                FollowerList.open()
            } else {
                FollowerList.close()
            }
        };
        this.init()
    }
    FollowerList();
    $(".hentry").livequery("click", function () {
        currentPosition = $(this);
        $(".hentry").removeClass("focus");
        currentPosition.addClass("focus")
    });
    $("textarea#status, textarea#text").focus(function () {
        $("textarea#status, textarea#text").css("height", "90px");
        $("div.bar,div.status-btn,#currently").show()
    });
    $("textarea#status, textarea#text").blur();

    function removeDummy() {
        if ($("#results_update").css("display") == "none") {
            return false
        } else {
            $(".section.grid li.buffered, .section.grid li.unbuffered").remove()
        }
    }
    setInterval("removeDummy()", 600);

    function repaint() {}
    $(window).resize(function () {
        repaint()
    });
    focus(document.body);
    $("head").append('<script type="text/javascript" src="' + srcURL + 'lib/jquery-pagination/jquery.pagination.js"/>')
};