// <#--  查看更多按钮  -->
var formElemt = '<span class="form-more">更多</span>'
    function moreForm() {
        // 获取当前表单总高度
        // console.log($,'====');
        console.log($('.layuiadmin-card-header-auto .layui-form-item'));
        var formHeight = $('.layuiadmin-card-header-auto .layui-form-item')[0].clientHeight // 表单总高度
        var formParentH = $('.layui-card-header.layuiadmin-card-header-auto')[0].clientHeight // 表单元素父级高度
        $('.layui-card-header.layuiadmin-card-header-auto').siblings('.layui-card-body').css('padding-top', 0)
        console.log(formHeight, formParentH)
        // 如若当前表单元素高度大于一行  则执行下拉展示查看更多操作
        if (formHeight > formParentH) { // 如果表单高度大于当前父级表单高度则默认一行展示 点击查看更多
            $('.layuiadmin-card-header-auto').append(formElemt)
            $('.layui-card-header.layuiadmin-card-header-auto .layui-form-item').css('flex', 1)
        }
        $('.layui-form').on('click', '.form-more', function () {
            if ($(this).text() === '更多') {
                console.log($(this).text())
                $(this).parents('.layui-card-header.layuiadmin-card-header-auto')[0].style.height = 'inherit'
                $(this).text('收起')
            } else {
                $(this).parents('.layui-card-header.layuiadmin-card-header-auto')[0].style.height = 50 + 'px'
                $(this).text('更多')
            }
        })
    }
    $('document').ready(function () {
        moreForm()
        $('.layui-card-header.layuiadmin-card-header-auto').css('overflow', 'hidden')
        setTimeout(function () {
            $('.layui-card-header.layuiadmin-card-header-auto').css('overflow', 'initial')
        }, 1000)

    })
