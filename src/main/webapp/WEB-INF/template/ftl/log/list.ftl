<#import '/WEB-INF/template/ftl/inc/inc.ftl' as inc />
<#assign title='交易记录'>
<#assign nav='3'>
<@inc.header title>
<link rel="stylesheet" href="${ctx}css/bootstrap-datetimepicker.min.css">
<script src="${ctx}js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}js/jqPaginator.min.js"></script>
</@inc.header>
<@inc.body>
<div class="container" role="main">
    <div class="row">
        <div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>姓名</th>
                    <th>操作类型</th>
                    <th>发生金额</th>
                    <th>时间</th>
                    <th>账户余额</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tbody>
                    <#list data as item>
                    <tr>
                        <td> ${item.userName}</td>
                        <td>
                            <script>if (${item.type}==2
                            )
                            document.write("<code>充值</code>");
                            else
                            document.write("消费");</script>
                        </td>
                        <td>${item.money}</td>
                        <td>
                            <script>document.write(new Date(parseInt(${item.addtime})).toLocaleString());</script>
                        </td>
                        <td>${item.leftMoney}</td>
                        <td>${item.content}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <ul id="page" class="pagination">

        </ul>

    </div>
</div>
</@inc.body>
<@inc.footer nav>
<script>
    $('.datetimepicker').datetimepicker({
        format: "yyyy-mm-dd",
        todayBtn: "linked",
        language: "zh-CN",
        minView: 2,
        autoclose: true
    });
    $('#page').jqPaginator({
        totalCounts:${totalCounts} ,
        pageSize: 15,
        visiblePages: 5,
        currentPage:${currentPage},

        first: '<li><a href="${ctx}log.htm?userId=${userId}&pageNo=1&pageSize=15">首页</a></li>',
        prev: '<li><a href="${ctx}log.htm?userId=${userId}&pageNo=${currentPage-1}&pageSize=15"><i class="ico-arrow ico-arrow2"></i>上一页</a></li>',
        next: '<li><a href="${ctx}log.htm?userId=${userId}&pageNo=${currentPage+1}&pageSize=15">下一页<i class="ico-arrow ico-arrow3"></i></a></li>',
        last: '<li><a href="${ctx}log.htm?userId=${userId}&pageNo={{totalPages}}&pageSize=15">末页</a></li>',
        page: '<li><a href="${ctx}log.htm?userId=${userId}&pageNo={{page}}&pageSize=15">{{page}}</a></li>'

    });

</script>
</@inc.footer>