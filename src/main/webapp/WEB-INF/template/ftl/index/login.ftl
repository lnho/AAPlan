<#import '/WEB-INF/template/ftl/inc/inc.ftl' as inc />
<#assign title='首页'>
<#assign nav='1'>
<@inc.header title>

</@inc.header>
<@inc.body>
<style>
    .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
    }
</style>
<div class="container">
    <form class="form-signin" action="${ctx}index/login.htm" method="post">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputEmail" class="sr-only">帐号</label>
        <input name="userName" type="text" id="inputUserName" class="form-control" placeholder="帐号" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required>

        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>
</div>
</@inc.body>
<@inc.footer nav>
</@inc.footer>
