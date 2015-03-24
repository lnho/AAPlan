<#global ctx = '${ctx}'>
<#macro header title>
<!DOCTYPE HTML>
<html>
<head>
    <title>${title!""}</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <#nested>
</head>
</#macro>
<#macro body>
<body>
    <div class="navbar navbar-inverse navbar-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target=".navbar-collapse">
                    <span class="sr-only"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <a class="navbar-brand" href="${ctx}">饭团管理系统</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li id="nav1"><a href="${ctx}">首页</a></li>
                    <#if isLogin=true>
                        <li id="nav2"><a href="${ctx}user.htm">账户资产</a></li>
                    </#if>
                    <li id="nav3"><a href="${ctx}log.htm">交易记录</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if isLogin=true>
                        <li><a href="${ctx}index/logout.htm">退出</a></li>
                    <#else>
                        <li><a href="${ctx}index/login.htm">我是管理员</a></li>
                    </#if>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    <#nested>
</#macro>

<#macro footer nav>
<script>
    $('#nav${nav!'1'}').attr("class", "active");
</script>
    <#nested>
<footer style="text-align: center;">
    ©2015 AAPlan
</footer>
</body>
</html>
</#macro>