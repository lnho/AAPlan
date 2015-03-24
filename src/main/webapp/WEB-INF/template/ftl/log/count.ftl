<#import '/WEB-INF/template/ftl/inc/inc.ftl' as inc />
<#assign title='交易记录'>
<#assign nav='3'>
<@inc.header title>
<link rel="stylesheet" href="${ctx}css/bootstrap-datetimepicker.min.css">
<script src="${ctx}js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}js/jqPaginator.min.js"></script>
<style>
    .search {
        margin-bottom: 20px;
    }
</style>
</@inc.header>
<@inc.body>
<div class="container" role="main">
    <div class="row">
        <div>
            <h2>${userName}</h2>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>统计时间</th>
                    <th>消费金额</th>
                </tr>
                </thead>
                <tbody>
                    <#list list as item>
                    <tr>
                        <td>${item.months}</td>
                        <td>${item.count}</td>
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