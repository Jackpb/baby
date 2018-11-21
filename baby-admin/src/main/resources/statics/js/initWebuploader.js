function initWebuploader() {
    // 初始化Web Uploader
    var uploader = WebUploader.create({

        // 选完文件后，是否自动上传。
        auto: true,
        // swf文件路径
        swf: baseURL + 'statics/plugins/ueditor/third-party/webuploader/Uploader.swf',

        // 文件接收服务端。
        server:  baseURL +  "ueditor/ueditor/uploadOne",

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
        // 只允许选择图片文件。
        // accept: {
        //     title: 'Images',
        //     extensions: 'gif,jpg,jpeg,bmp,png',
        //     mimeTypes: 'image/*'
        // }
    });

    // 当有文件被添加进队列的时候
    uploader.on( 'fileQueued', function( file ) {
        // $("#thelist").append( '<div id="' + file.id + '" class="item">' +
        //     '<h4 class="info">' + file.name + '</h4>' +
        //     '<p class="state">等待上传...</p>' +
        //     '</div>' );
        $("#thelist").empty();
        $("#thelist").append( '<div id="' + file.id + '" class="item">' +
            '<p class="state">等待上传...</p>' +
            '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css( 'width', percentage * 100 + '%' );
    });

    //判断上传是否成功
    uploader.on( 'uploadAccept', function( file, r ) {
        if(r.state=='FAIL'){
            return false;
        }else{
            return true;
        }
    });

    uploader.on( 'uploadSuccess', function( file,r ) {

        $("#material_url").val(r.url).change();
        // $( '#'+file.id ).find('p.state').text('已上传');
        $( '#'+file.id ).find('p.state').remove();
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错，请检查网络连接是否正常！');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

}