<#import '/WEB-INF/template/ftl/inc/inc.ftl' as inc />
<#assign title='账户资产'>
<#assign nav='2'>
<@inc.header title>

</@inc.header>
<@inc.body>
<div class="container">
    <div class="row">
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
                    <td> ${item.username}</td>
                    <td>${item.money}</td>
                    <td>
                        <button type="button" class="btn btn-default"
                                onclick="dialog(1,${item.id},'${item.username}')">充值
                        </button>
                        &nbsp;
                        <button type="button" class="btn btn-primary"
                                onclick="dialog(2,${item.id},'${item.username}')">消费
                        </button>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="" method="post" id="form1">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="money">金额</label>
                        <input type="text" class="form-control" id="money" name="money" placeholder="金额">
                    </div>
                    <div class="form-group">
                        <label for="money">备注</label>
                        <input type="text" class="form-control" id="content" name="content" placeholder="备注">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="getResult();">确定</button>
                </div>
            </form>
        </div>
    </div>
</div>
</@inc.body>
<@inc.footer nav>
<script>
    var userid;
    var type;
    function dialog(type1, userid1, username) {
        userid = userid1;
        if (type1 == 1) {
            $('#myModalLabel').html("给" + username + "充值");
            type = "charge";
        } else {
            $('#myModalLabel').html("给" + username + "消费");
            type = "pay";
        }
        $('#myModal').modal();
    }
    function getResult() {
        $.post('${ctx}user/' + type + '.htm', {id: userid, money: $('#money').val(), content: $('#content').val()}, function (data, status) {
            location.reload();
        });

    }
</script>
</@inc.footer>
