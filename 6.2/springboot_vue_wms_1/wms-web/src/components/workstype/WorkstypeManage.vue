<template>
    <div>
        <div style="margin-bottom: 5px">
            <el-input
                v-model="name"
                placeholder="请输入作业分类名"
                suffix-icon="el-icon-search"
                style="width: 190px"
                @keyup.enter.n.native="loadPost"
            ></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
            <el-button type="info" @click="resetParam">重置</el-button>
            <el-button type="danger" @click="add" v-if="user.roleId == 0">新增</el-button>
        </div>
        <el-table
            :data="tableData"
            :header-cell-style="{
                background: 'rgba(43,65,134,0.41)',
                color: 'rgba(72,35,35,0.61)',
            }"
            border
            :cell-style="{ borderColor: 'rgba(35,36,38,0.97)' }"
        >
            <el-table-column prop="id" label="ID" width="80"> </el-table-column>
            <el-table-column prop="name" label="作业分类名" width="180"> </el-table-column>
            <el-table-column prop="remark" label="备注"> </el-table-column>
            <el-table-column prop="operate" label="操作">
                <template slot-scope="scope">
                    <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm
                        title="确定删除吗？"
                        @confirm="del(scope.row.id)"
                        style="margin-left: 15px"
                    >
                        <el-button slot="reference" size="small" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
        </el-pagination>

        <el-dialog title="提示" :visible.sync="centerDialogVisible" width="30%" center>
            <el-form ref="form" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="分类名" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form.remark"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'WorkstypeManege',
    data() {
        return {
            user: JSON.parse(sessionStorage.getItem('CurUser')),
            tableData: [],
            pageNum: 1,
            pageSize: 5,
            total: 0,
            name: '',

            centerDialogVisible: false,
            form: {
                id: '',
                name: '',
                remark: '',
            },
            rules: {
                name: [{ required: true, message: '请输入作业分类名字', trigger: 'blur' }],
            },
        }
    },
    methods: {
        //post请求
        loadPost() {
            this.$axios
                .post(this.$httpUrl + '/workstype/listPage', {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        name: this.name,
                    },
                })
                .then((res) => res.data)
                .then((res) => {
                    console.log(res)
                    if (res.code == 200) {
                        this.tableData = res.data
                        this.total = res.total
                    } else {
                        alert('获取数据失败')
                    }
                })
        },
        //分页方法
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`)
            //this.pageNum=1 防止前端更改页数的时候报错倒是可加可不加
            this.pageSize = val
            this.loadPost()
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`)
            this.pageNum = val
            this.loadPost()
        },
        resetParam() {
            this.name = ''
        },
        add() {
            this.centerDialogVisible = true
            this.$nextTick(() => {
                this.resetForm()
            })
        },
        doSave() {
            this.$axios
                .post(this.$httpUrl + '/workstype/save', this.form)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res)
                    if (res.code == 200) {
                        //alert('添加成功')
                        this.$message({
                            message: '添加成功',
                            type: 'success',
                        })
                        this.centerDialogVisible = false
                        this.loadPost()
                    } else {
                        //alert('添加失败')
                        this.$message({
                            message: '添加失败',
                            type: 'error',
                        })
                    }
                })
        },
        doMod() {
            this.$axios
                .post(this.$httpUrl + '/workstype/update ', this.form)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res)
                    if (res.code == 200) {
                        //alert('添加成功')
                        this.$message({
                            message: '添加成功',
                            type: 'success',
                        })
                        this.centerDialogVisible = false
                        this.loadPost()
                    } else {
                        //alert('添加失败')
                        this.$message({
                            message: '添加失败',
                            type: 'error',
                        })
                    }
                })
        },
        save() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    //如果通过了才执行调用后端否则不执行
                    if (this.form.id) {
                        this.doMod()
                    } else {
                        this.doSave()
                    }
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        resetForm() {
            this.$refs.form.resetFields()
        },

        mod(row) {
            console.log(row)

            this.centerDialogVisible = true
            this.$nextTick(() => {
                //复制到表单
                this.form.id = row.id
                this.form.name = row.name
                this.form.remark = row.remark
            })
        },
        del(id) {
            console.log(id)
            this.$axios
                .get(this.$httpUrl + '/workstype/del?id=' + id)
                .then((res) => res.data)
                .then((res) => {
                    console.log(res)
                    if (res.code == 200) {
                        //alert('添加成功')
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                        })
                        this.loadPost()
                    } else {
                        //alert('添加失败')
                        this.$message({
                            message: '删除失败',
                            type: 'error',
                        })
                    }
                })
        },
    }, //methods方法到这里截至
    beforeMount() {
        this.loadPost()
    },
}
</script>

<style scoped></style>
