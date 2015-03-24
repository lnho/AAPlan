<#import '/WEB-INF/template/ftl/inc/inc.ftl' as inc />
<#assign title='首页'>
<#assign nav='1'>
<@inc.header title>
</@inc.header>
<@inc.body>

<div class="container">
    <div class="row">
        <div class="col-md-3 sidebar">
            <div class="">
                <h4>饭团健康状况</h4>

                <p>账户余额： ${remainMoney}元</p>

                <p>可以养活： 6人${remainDays}天</p>
            </div>
        </div>
        <div class="col-md-9 main">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>姓名</th>
                    <th>账户余额</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list data as item>
                    <tr>
                        <td>${item.username}</td>
                        <td>
                            <#if item.money<0>
                                <span class="label label-danger">${item.money}</span>
                            <#else>
                            ${item.money}
                            </#if>
                        </td>
                        <td>
                            <a class="btn btn-primary" href="${ctx}log.htm?userId=${item.id}">我要对账</a>
                            <a class="btn btn-primary" href="${ctx}log/count.htm?userId=${item.id}">月消费情况</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</@inc.body>
<@inc.footer nav>
</@inc.footer>
