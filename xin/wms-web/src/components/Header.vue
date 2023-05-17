<template>
    <div style="display: flex; line-height: 60px">
        <!--    //这是左侧图标居中显示下面这个div的作用-->
        <div style="margin-top: 6px">
            <i :class="icon" style="font-size: 20px; cursor: pointer" @click="collapse"></i>
        </div>
        <div style="flex: 1; text-align: center; font-size: 35px">
            <span>欢迎来到作业管理与评价系统</span>
        </div>
        <el-badge
            v-if="tableData.length && user.roleId != 0"
            :value="tableData.length"
            class="item"
            style="margin-right: 30px; cursor: pointer"
        >
            <i style="font-size: 20px" class="el-icon-message-solid" @click="showMessage"></i>
        </el-badge>
        <el-dropdown>
            <span>{{ user.name }}</span
            ><i class="el-icon-s-home" style="font-size: 16px; margin-left: 6px"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                <!--        <el-dropdown-item>删除</el-dropdown-item>-->
            </el-dropdown-menu>
        </el-dropdown>

        <el-dialog
            style="text-align: left; line-height: 0px"
            title="未读公告"
            :visible.sync="show"
            :before-close="() => (show = false)"
        >
            <el-table :data="tableData">
                <el-table-column prop="title" label="公告标题" width="180"> </el-table-column>
                <el-table-column prop="content" label="公告内容"> </el-table-column>
                <el-table-column prop="createTime" label="发布时间"> </el-table-column>

                <el-table-column prop="operate" label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" type="info" @click="read(scope.row)"
                            >已阅</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'Header',
    data() {
        return {
            user: JSON.parse(sessionStorage.getItem('CurUser')),
            show: false,
            tableData: [],
        }
    },
    props: {
        icon: String,
    },
    methods: {
        toUser() {
            console.log('to_user')
            this.$router.push('/Home')
        },
        logout() {
            console.log('to_logout')

            this.$confirm('您确定要退出登录吗?', '提示', {
                confirmButtonText: '确定', //确认按钮的文字显示
                type: 'warning',
                center: true, //文字居中显示
            })
                .then(() => {
                    this.$message({
                        type: 'success',
                        message: '退出登录成功',
                    })

                    this.$router.push('/')
                    sessionStorage.clear()
                })
                .catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消退出登录',
                    })
                })
        },
        collapse() {
            this.$emit('doCollapse')
        },
        showMessage() {
            this.show = true
        },
        getNotice() {
            this.$axios
                .get(this.$httpUrl + '/notice/countUnread', {
                    params: {
                        id: this.user.id,
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    if (res.code == 200) {
                        this.tableData = res.data
                    } else {
                        alert('获取数据失败')
                    }
                })
        },
        read(row) {
            this.$axios
                .post(this.$httpUrl + '/notice/read', {
                    nid: row.id,
                    uid: this.user.id,
                    status: 1,
                })
                .then(() => {
                    this.getNotice()
                })
        },
    },
    created() {
        this.$router.push('/Home')
        this.getNotice()
    },
}
</script>

<style>
.el-badge__content.is-fixed {
    position: absolute;
    top: 0;
    right: 10px;
    transform: translateY(3%) translateX(100%);
}
</style>
