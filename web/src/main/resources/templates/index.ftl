<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="shortcut icon" href="${request.contextPath}/static/favicon.ico"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/index.css" media="all">
    <style>
        .currentZtreeNode p {
            /*display: initial;*/
            width: 100%;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
    </style>
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <#-- 横向菜单-->
        <#if menuType == 'horizontal'>
            <div class="horizontal-menu">
                <div class="sys-title">企业CMS管理系统</div>
                <div class="sys-menu">
                    <ul class="menu-list" lay-filter="layadmin-system-side-menu">
                        <#if sysMenus?? && (sysMenus?size > 0)>
                            <#list sysMenus as menu>
                                <li class="first-menu">
                                    <#if menu.menuType ==1 && menu.menuUrl!="" && (!menu.childs)>
                                        <a lay-href="${menu.menuUrl}">${menu.menuName}</a>
                                    </#if>
                                    <p class="menu-title">
                                        <span class="title">${menu.menuName}</span>
                                        <span class="subtitle">${menu.menuCode}</span>
                                        <#if menu.childs>
                                            <i class="nav-more-close"></i>
                                        </#if>
                                    </p>
                                    <#if menu.childs>
                                        <dl class="second-menu">
                                            <#list menu.childs as secondMenu>
                                                <dd data-name="${secondMenu.menuCode}">
                                                    <a lay-href="${secondMenu.menuUrl}">${secondMenu.menuName}</a>
                                                </dd>
                                            </#list>
                                        </dl>
                                    </#if>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </div>
                <div class="more-menu">
                    <i class="layui-icon layui-icon-more"></i>
                    <ul class="menu-list" lay-filter="layadmin-system-side-menu">
                    </ul>
                </div>
            </div>
        </#if>
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left layui-layout-common">
                <li class="layui-nav-item layadmin-flexible" lay-unselect>
                    <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right layui-layout-common" lay-filter="layadmin-layout-right">
                <li class="layui-nav-item" lay-unselect>
                    <a lay-href="message/msgList.html" layadmin-event="message" lay-text="消息中心">
                        <i class="layui-icon layui-icon-notice"></i>

                        <!-- 如果有新消息，则显示小圆点 -->
                        <span class="layui-badge-dot"></span>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;" layadmin-event="fullscreen">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;">
                        <cite>${user.username}</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a lay-href="user/info.html?_=${randomNum}">基本资料</a></dd>
                        <dd><a lay-href="user/password.html?_=${randomNum}">修改密码</a></dd>
                        <dd><a href="javascript:;" id="changeMenu">切换菜单</a></dd>
                        <dd style="text-align: center;"><a lay-href="sign/logout">退出</a></dd>
                    </dl>
                </li>

                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;"><i
                                class="layui-icon layui-icon-more-vertical"></i></a>
                </li>
                <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
                    <a href="javascript:;"><i class="layui-icon layui-icon-more-vertical"></i></a>
                </li>
            </ul>
        </div>
        <!-- 竖向菜单 -->
        <#if menuType == 'vertical'>
            <div class="layui-side layui-side-menu">
                <div class="layui-side-scroll">
                    <div class="layui-logo" lay-href="main.html">
                        <span>陈天狼VS邓波波</span>
                    </div>
                    <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu"
                        lay-filter="layadmin-system-side-menu">
                        <#list sysMenus as menu>
                            <li data-name="component" class="layui-nav-item">
                                <a href="javascript:;" lay-direction="2">
                                    <i class="layui-icon ${menu.menuIcon}"></i>
                                    <cite>${menu.menuName}</cite>
                                </a>
                                <#if menu.childs>
                                    <dl class="layui-nav-child">
                                        <#list menu.childs as secondMenu>
                                            <#if secondMenu.childs>
                                                <dd data-name="grid">
                                                    <a href="javascript:;">${secondMenu.menuName}</a>
                                                    <dl class="layui-nav-child">
                                                        <#list secondMenu.childs as threeMenu>
                                                            <dd data-name="${threeMenu.menuCode}">
                                                                <a lay-href="${threeMenu.menuUrl}"
                                                                   class="currentZtreeNode">
                                                                    <p><span>${threeMenu.menuName}</span></p>
                                                                </a></dd>
                                                        </#list>
                                                    </dl>
                                                </dd>
                                            <#else>
                                                <dd data-name="${secondMenu.menuCode}">
                                                    <a lay-href="${secondMenu.menuUrl}" class="currentZtreeNode">
                                                        <p><span>${secondMenu.menuName}</span></p>
                                                    </a>
                                                </dd>
                                            </#if>
                                        </#list>
                                    </dl>
                                    <div class="hoverStyle">
                                    </div>
                                </#if>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>
        <!-- 页面标签 -->
        <div class="layadmin-pagetabs layadmin-header-index" id="LAY_app_tabs">
            <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-down">
                <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                    <li class="layui-nav-item" lay-unselect>
                        <a href="javascript:;"></a>
                        <dl class="layui-nav-child layui-anim-fadein">
                            <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                            <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                            <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li lay-id="main.html" lay-attr="main.html" class="layui-this"><i
                                class="layui-icon layui-icon-home"></i></li>
                </ul>
            </div>
        </div>


        <!-- 主体内容 -->
        <div class="layui-body" id="LAY_app_body">
            <div class="layadmin-tabsbody-item layui-show">
                <iframe src="main.html" frameborder="0" class="layadmin-iframe"></iframe>
            </div>
        </div>

        <!-- 辅助元素，一般用于移动设备下遮罩 -->
        <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/watermark/watermark.js?_=${randomNum}"></script>
<script>
    layui.config({
        base: '${request.contextPath}/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use('index');
</script>

<!-- 百度统计 -->
<script>
    var _hmt = _hmt || [];
    (function () {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?d214947968792b839fd669a4decaaffc";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>

<#-- 侧边栏悬浮提示子级数据 -->
<script>
    var iswatermark = "${watermark}";
    if (iswatermark) {
        watermark.load({ watermark_txt: "水印内容 xxxx 2020-1-1 9:00:00" });
    }
    var menuType = "${menuType}";
    if (menuType === 'vertical') {
        var currentTree = document.getElementsByClassName('layui-nav-tree') // 当前nav导航元素
        for (var i = 0; i < currentTree[0].children.length; i++) { // 循环导航元素的层级
            //  鼠标移入对应模块的时候获取对应的子级模块的值
            currentTree[0].children[i].onmouseenter = function (e) {
                if (document.getElementsByClassName('layui-side')[0].clientWidth === 60) { // 根据判断当前为收起状态则进行下一步处理
                    if (this === e.target) { // 如果鼠标触发的当前元素与当前子级相一致则进行下一步处理
                        var showChild = this.children[1].children
                        var arry = Array.prototype.slice.call(showChild) //将html结构集合转为数组形式
                        arry.reverse() // 将数组中的值倒序  防止位置有差异
                        for (var j = arry.length - 1; j >= 0; j--) {
                            this.children[2].appendChild(arry[j]) // 将导航中的子级元素结构赋值给悬浮提示元素
                            arry[j].children[0].onclick = function () {
                                for (var l = 0; l < this.parentElement.parentElement.children.length; l++) {
                                    this.parentElement.parentElement.children[l].children[0].className = 'currentZtreeNode'
                                    this.className = 'currentZtreeNode activeStatus'
                                }
                            }
                        }
                        this.children[2].style.display = 'block' // 给与当前选中的元素的子级显示状态
                    }
                }
            }
            //     鼠标离开时
            currentTree[0].children[i].onmouseleave = function (e) {
                if (document.getElementsByClassName('layui-side')[0].clientWidth === 60) { // 根据判断当前为收起状态则进行下一步处理
                    if (this === e.target) { // 如果鼠标触发的当前元素与当前子级相一致则进行下一步处理
                        this.children[2].style.display = 'none'
                    }
                }
            }
            //     获取点击事件
            var hrefList = currentTree[0].children[i].getElementsByTagName("a")
            for (var p = 0; p < hrefList.length; p++) {
                if (hrefList[p].href === 'javascript:;') {
                    hrefList[p].onclick = function (e) {
                        if (document.getElementsByClassName('layui-side')[0].clientWidth === 60) {
                            //     二级菜单
                            if (this.nextElementSibling.nextElementSibling !== null) {
                                var currentHover = this.nextElementSibling.nextElementSibling.children
                                var currentEle = Array.prototype.slice.call(currentHover) //将html结构集合转为数组形式
                                currentEle.reverse() // 将数组中的值倒序 防止位置有差异
                                for (var j = currentEle.length - 1; j >= 0; j--) {
                                    this.nextElementSibling.appendChild(currentEle[j]) // 给原本的展示元素父级添加子级 防止为空值
                                    this.nextElementSibling.nextElementSibling.style.display = 'none'
                                }
                                // 除了当前元素外  给所有的同级元素赋值
                                var allSilbing = this.parentElement.parentElement.children
                                for (var c = 0; c < allSilbing.length; c++) {
                                    if (Array.prototype.slice.call(allSilbing[c].children).length > 0) {
                                        if (allSilbing[c].children[2].children.length > 0) {
                                            var currentZtree = Array.prototype.slice.call(allSilbing[c].children[2].children)
                                            currentZtree.reverse()
                                            for (var n = currentZtree.length - 1; n >= 0; n--) {
                                                allSilbing[c].children[1].appendChild(currentZtree[n])
                                            }
                                        }
                                    }
                                }
                            } else {
                                //  三级菜单获取对应的值放置在nav中
                                this.parentElement.parentElement.style.display = 'none'
                                var currentSilbing = this.parentElement.parentElement.previousSibling.previousElementSibling // 三级菜单的元素级别
                                var currentTree = this.parentElement.parentElement.children
                                var treeElement = Array.prototype.slice.call(currentTree)
                                treeElement.reverse() // 将数组中的值倒序 防止位置有差异
                                for (var p = treeElement.length - 1; p >= 0; p--) {
                                    currentSilbing.appendChild(treeElement[p])
                                }
                            }
                        }
                    }
                }
            }
        }

        /// 收缩事件处理 侧边栏对应的值
        var docuemntEle = document.getElementsByClassName('layui-side') // 侧边栏宽度
        var navElement = document.getElementById('LAY_app_flexible') // 收缩操作图标
        var hoverElement = document.getElementsByClassName('hoverStyle') // 含有悬浮字段的结构
        navElement.onclick = function () {
            if (docuemntEle[0].clientWidth === 60) {
                for (var p = 0; p < hoverElement.length; p++) {
                    if (hoverElement[p].children.length !== 0) { // 如果对应的 hoverStyle 值不为空
                        var childArray = Array.prototype.slice.call(hoverElement[p].children)
                        childArray.reverse()
                        for (var i = childArray.length - 1; i >= 0; i--) {
                            hoverElement[p].previousSibling.previousSibling.appendChild(childArray[i]) // 给 layui-nav-child 赋予 hoverStyle 中的值
                        }
                    }
                }
            }
        }
        // 页面屏幕大小检测
        window.onresize = function () {
            for (var i = 0; i < hoverElement.length; i++) {
                if (hoverElement[i].children.length > 0) {
                    var childArray = Array.prototype.slice.call(hoverElement[i].children)
                    childArray.reverse()
                    for (var p = childArray.length - 1; p >= 0; p--) {
                        hoverElement[i].previousSibling.previousSibling.appendChild(childArray[p])
                    }
                }
            }
        }

        //    2019-09-29 cx
        function nodeStatus(className) {
            var ZtreeNode = document.getElementsByClassName(className)
            for (var i = 0; i < ZtreeNode.length; i++) {
                ZtreeNode[i].children[0].onmouseenter = function (e) {
                    var parWid = this.clientWidth
                    var childWid = this.children[0].offsetWidth
                    if (childWid > parWid) {
                        layer.tips(this.children[0].innerHTML, this);
                    }
                }
            }
        }

        nodeStatus('currentZtreeNode')
    }
    layui.use(['jquery'], function () {
        var $ = layui.$;
        $('#changeMenu').on('click', function () {
            if (menuType === 'vertical') {
                location.replace(changeURLArg(window.location.href, 'menuType', 'horizontal'));
            } else {
                location.replace(changeURLArg(window.location.href, 'menuType', 'vertical'));
            }
        });

        function changeURLArg(url, arg, arg_val) {
            var pattern = arg + '=([^&]*)';
            var replaceText = arg + '=' + arg_val;
            if (url.match(pattern)) {
                var tmp = '/(' + arg + '=)([^&]*)/gi';
                tmp = url.replace(eval(tmp), replaceText);
                return tmp;
            } else {
                if (url.match('[\?]')) {
                    return url + '&' + replaceText;
                } else {
                    return url + '?' + replaceText;
                }
            }
            return url + '\n' + arg + '\n' + arg_val;
        }
    });
</script>

<script>
    var menuType = "${menuType}";
    if (menuType === 'horizontal') {
        document.getElementById('LAY_app').className = 'horizontal-type';
        layui.use(['jquery', 'element'], function () {
            var $ = layui.$;
            var element = layui.element;
            computeMenu();
            $('.more-menu .menu-list').css("max-height", document.body.clientHeight * 0.65 + 'px');
            $(document).on('mouseenter', '.sys-menu .first-menu', function () {
                if ($(this).find('.second-menu')) {
                    $(this).find('.second-menu').stop(true, true).fadeIn('fast');
                }
            });
            $(document).on('mouseleave', '.sys-menu .first-menu', function () {
                if ($(this).find('.second-menu')) {
                    $(this).find('.second-menu').stop(true, true).fadeOut('fast');
                }
            });
            $(document).on('click', '.more-menu .first-menu .menu-title', function () {
                if ($(this).siblings('.second-menu')) {
                    if ($(this).siblings('.second-menu').css('display') === 'none') {
                        $(this).children('.nav-more-close').addClass('nav-more-open');
                        $(this).siblings('.second-menu').stop(true, true).slideDown('fast');
                    } else {
                        $(this).children('.nav-more-close').removeClass('nav-more-open');
                        $(this).siblings('.second-menu').stop(true, true).slideUp('fast');
                    }
                }
            });
            $('.more-menu').on('mouseenter', function () {
                if ($(this).find('.menu-list')) {
                    $(this).find('.menu-list').stop(true, true).fadeIn('fast');
                }
            });
            $('.more-menu').on('mouseleave', function () {
                if ($(this).find('.menu-list')) {
                    $(this).find('.menu-list').stop(true, true).fadeOut('fast');
                }
            });
            $(document).on('click', '.first-menu', function () {
                setTimeout(function () {
                    linkage();
                }, 0);
            });
            $("#LAY_app_tabsheader").on('click', function () {
                setTimeout(function () {
                    linkage();
                }, 0);
            });
            window.onresize = function () {
                computeMenu();
                $('.more-menu .menu-list').css("max-height", document.body.clientHeight * 0.6 + 'px');
            }

            function computeMenu() {
                var menuLength = $('.sys-menu .menu-list').children().length + $('.more-menu .menu-list').children().length;
                for (var i = 0; i < $('.more-menu .menu-list').children().length; i++) {
                    if ($('.more-menu .menu-list').children().eq(i).children('.second-menu').length) {
                        $('.more-menu .menu-list').children().eq(i).children('.menu-title').children('.nav-more-close').removeClass('nav-more-open');
                        $('.more-menu .menu-list').children().eq(i).children('.second-menu').css('display', 'none');
                    }
                }
                if (Math.floor($('.sys-menu').width() / 168) >= menuLength) {
                    $('.more-menu').hide();
                    $('.sys-menu').css("margin-right", "20px");
                    if ($('.more-menu .menu-list').children().length) {
                        $('.sys-menu .menu-list').append($('.more-menu .menu-list').children());
                    }
                } else {
                    $('.sys-menu').css("margin-right", "75px");
                    $('.more-menu').show();
                    var moreLength = menuLength - Math.floor($('.sys-menu').width() / 168);
                    var ictLength = $('.sys-menu .menu-list').children().length;
                    if (moreLength > $('.more-menu .menu-list').children().length) {
                        moreLength = moreLength - $('.more-menu .menu-list').children().length;
                        for (var i = 1; i <= moreLength; i++) {
                            $('.more-menu .menu-list').prepend($('.sys-menu .menu-list').children().eq(ictLength - i));
                        }
                    } else if (moreLength < $('.more-menu .menu-list').children().length) {
                        var eleList = [];
                        moreLength = $('.more-menu .menu-list').children().length - moreLength;
                        for (var i = 0; i < moreLength; i++) {
                            eleList.push($('.more-menu .menu-list').children().eq(i));
                        }
                        for (var j = 0; j < eleList.length; j++) {
                            $('.sys-menu .menu-list').append(eleList[j]);
                        }
                    }
                }
            }

            function linkage() {
                for (var i = 0; i < $('.first-menu').length; i++) {
                    if ($('.first-menu').eq(i).hasClass('layui-this')) {
                        $('.first-menu').eq(i).removeClass('layui-this');
                        if ($('.first-menu').eq(i).children('.second-menu').length) {
                            for (var j = 0; j < $('.first-menu').eq(i).children('.second-menu').children().length; j++) {
                                if ($('.first-menu').eq(i).children('.second-menu').children().eq(j).hasClass('layui-this')) {
                                    $('.first-menu').eq(i).children('.second-menu').children().eq(j).removeClass('layui-this');
                                }
                            }
                        }
                    }
                }
                for (var i = 0; i < $('#LAY_app_tabsheader').children().length; i++) {
                    if ($('#LAY_app_tabsheader').children().eq(i).hasClass('layui-this')) {
                        var currentPage = $('#LAY_app_tabsheader').children().eq(i).attr('lay-id');
                        for (var j = 0; j < $('.first-menu').length; j++) {
                            if ($('.first-menu').eq(j).children('a').length && $('.first-menu').eq(j).children('a').attr('lay-href') !== '') {
                                if ($('.first-menu').eq(j).children('a').attr('lay-href') === currentPage) {
                                    $('.first-menu').eq(j).addClass('layui-this');
                                }
                            } else {
                                if ($('.first-menu').eq(j).children('.second-menu').length) {
                                    for (var k = 0; k < $('.first-menu').eq(j).children('.second-menu').children().length; k++) {
                                        if ($('.first-menu').eq(j).children('.second-menu').children().eq(k).children('a').attr('lay-href') === currentPage) {
                                            $('.first-menu').eq(j).addClass('layui-this');
                                            $('.first-menu').eq(j).children('.second-menu').children().eq(k).addClass('layui-this');
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        });
    }
</script>
</body>
</html>