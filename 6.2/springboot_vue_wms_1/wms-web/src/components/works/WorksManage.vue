<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input
        v-model="name"
        placeholder="请输入作业名"
        suffix-icon="el-icon-search"
        style="width: 190px"
        @keyup.enter.n.native="loadPost"
      ></el-input>
      <el-select v-model="course" placeholder="请选择课程" style="margin-left: 5px">
        <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id"> </el-option>
      </el-select>
      <el-select v-model="workstype" placeholder="请选择分类" style="margin-left: 5px">
        <el-option v-for="item in workstypeData" :key="item.id" :label="item.name" :value="item.id"> </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="info" @click="resetParam">重置</el-button>
      <el-button type="danger" v-if="user.roleId == 0" @click="add">新增</el-button>
      <el-button type="danger" @click="inWorks" v-if="user.roleId != 0">添加</el-button>
    </div>
    <el-table
      :data="tableData"
      :header-cell-style="{
        background: 'rgba(43,65,134,0.41)',
        color: 'rgba(72,35,35,0.61)'
      }"
      :cell-style="{ borderColor: 'rgba(35,36,38,0.97)' }"
      border
      highlight-current-row
      @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="80"> </el-table-column>
      <el-table-column prop="name" label="作业名" width="180"> </el-table-column>
      <el-table-column prop="course" label="课程" :formatter="formatCourse"> </el-table-column>
      <el-table-column prop="workstype" label="作业分类" :formatter="formatWorkstype"> </el-table-column>
      <el-table-column prop="count" label="数量"> </el-table-column>
      <el-table-column prop="score" label="得分"> </el-table-column>
      <el-table-column prop="remark" label="备注"> </el-table-column>
      <el-table-column label="上传作业">
        <template #default="{ row }">
          <el-upload
            v-if="user.roleId != 0"
            class="upload-demo"
            action="http://localhost:8004/upload/"
            :data="row"
            :before-upload="beforeUpload"
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-remove="handleRemove"
            :show-file-list="false"
            :file-list="row.files"
          >
            <el-button size="small" type="primary">上传文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传 PDF 格式</div>
          </el-upload>
          <div>
            <template v-for="file in row.files">
              <el-link :key="file.id" type="primary"
                ><span @click="handlePreview(file)">{{ file.fileName }}</span>
                <i @click="deleteFile(file)" class="el-icon-delete" style="cursor: pointor; padding-left: 10px"></i
              ></el-link>
            </template>
          </div>
        </template>
      </el-table-column>
      <!--      新添加的的 -->

      <!--  这里是作业提交截至 -->
      <el-table-column prop="operate" label="操作" width="250">
        <template slot-scope="scope">
          <el-button size="small" type="info" @click="handleScore(scope.row)">评分</el-button>
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)" style="margin-left: 15px">
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
        <el-form-item label="作业名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="课程" prop="course">
          <el-col :span="20">
            <el-select v-model="form.course" placeholder="请选择课程" style="margin-left: 5px">
              <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id"> </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="作业分类" prop="workstype">
          <el-col :span="20">
            <el-select v-model="form.workstype" placeholder="请选择分类" style="margin-left: 5px">
              <el-option v-for="item in workstypeData" :key="item.id" :label="item.name" :value="item.id"> </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form.count"></el-input>
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

    <el-dialog title="提示" :visible.sync="inDialogVisible" width="30%" center>
      <el-dialog width="75%" title="用户选择" :visible.sync="innerVisible" append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>
      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="作业名">
          <el-col :span="20">
            <el-input v-model="form1.worksname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人">
          <el-col :span="20">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="inDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doInWorks">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="评分" :visible.sync="scoreShow" :before-close="closeScore" width="40%">
      <el-form :model="form"  ref="socreForm" :rules="scoreRule" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="作业名：">
              {{ form.name }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程：" >
              {{ formatCourse(form) }}
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="得分：" prop="score">
              <el-input v-model.number="form.score" placeholder="请输入评分"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeScore">取 消</el-button>
        <el-button type="primary" @click="saveScore">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="作业查看"
      :visible.sync="pdfShow"
      :before-close="() => (pdfShow = false)"
      width="80%"
      v-if="pdfShow"
    >
      <iframe :src="pdfUrl" style="border: none; width: 100%; height: 600px"
        ><p>您的浏览器不支持 iframe 标签,请从列表中下载预览</p></iframe
      >
    </el-dialog>
  </div>
</template>

<script>
import SelectUser from '@/components/user/SelectUser'
export default {
  name: 'WorksManege',
  components: { SelectUser },
  data() {
    let checkCount = (rule, value, callback) => {
      if (value > 9999) {
        callback(new Error('数量输⼊过⼤'))
      } else {
        callback()
      }
    }
    return {
      pdfShow: false,
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      scoreShow: false,
      workstypeData: [],
      courseData: [],
      tableData: [],
      workstype: '',
      course: '',
      pageNum: 1,
      pageSize: 5,
      total: 0,
      name: '',
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible: false,
      currentRow: {},
      tempUser: {},
      uploadDialogVisible: false, // 是否显示上传对话框
      uploading: false, // 是否正在上传
      uploadForm: {}, // 上传表单数据
      csrfToken: null, // CSRF Token
      fileList: [],
      pdfUrl: '',
      form: {
        id: '',
        name: '',
        remark: '',
        course: '',
        workstype: '',
        count: ''
      },
      form1: {
        works: '',
        worksname: '',
        username: '',
        count: '',
        userid: '',
        adminId: '',
        remark: ''
        // action:'1'
      },
      scoreRule: {
        score: [
          { required: true, message: '请输入得分', trigger: 'blur' },
          { type: 'number', message: '必须为数字', trigger: 'blur' }
        ]
      },
      rules1: {},
      rules: {
        name: [{ required: true, message: '请输入作业名字', trigger: 'blur' }],
        course: [{ required: true, message: '请选择课程', trigger: 'blur' }],
        workstype: [{ required: true, message: '请选择分类', trigger: 'blur' }],
        count: [
          { required: true, message: '请输⼊数量', trigger: 'blur' },
          {
            pattern: /^([1-9][0-9]*){1,4}$/,
            message: '数量必须为正整数字',
            trigger: 'blur'
          },
          { validator: checkCount, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.loadCourse()
    this.loadWorkstype()
    this.loadPost()
  },

  methods: {
    handlePreview(row) {
      this.pdfShow = true
      this.pdfUrl = this.$httpUrl + row.filePath
      console.log(this.pdfUrl)
    },
    deleteFile(file) {
      this.$axios.get(this.$httpUrl + '/file/delete?id=' + file.id).then(() => {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        this.loadPost()
      })
    },

    //post请求
    loadPost() {
      this.$axios
        .post(this.$httpUrl + '/works/listPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            workstype: this.workstype + '',
            course: this.course + ''
          }
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
      this.course = ''
      this.workstype = ''
    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm()
        this.form.id = ''
      })
    },
    inWorks() {
      if (!this.currentRow.id) {
        alert('请选择作业')
        return
      }
      this.inDialogVisible = true
      this.$nextTick(() => {
        this.resetInForm()
        this.form.id = ''
      })
      this.form1.worksname = this.currentRow.name
      this.form1.works = this.currentRow.id
      this.form1.adminId = this.user.id
      // this.form1.action='1'
    },
    outWorks() {
      if (!this.currentRow.id) {
        alert('请选择作业')
        return
      }
      this.inDialogVisible = true
      this.$nextTick(() => {
        this.resetInForm()
        this.form.id = ''
      })
      this.form1.worksname = this.currentRow.name
      this.form1.works = this.currentRow.id
      this.form1.adminId = this.user.id
      // this.form1.action='2'
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          //如果通过了才执行调用后端否则不执行
          this.form.files = this.fileList
          this.doSave()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + '/works/save', this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res)
          if (res.code == 200) {
            //alert('添加成功')
            this.$message({
              message: '操作成功',
              type: 'success'
            })
            this.centerDialogVisible = false
            this.loadPost()
            this.closeScore()
          } else {
            //alert('添加失败')
            this.$message({
              message: '操作失败',
              type: 'error'
            })
          }
        })
    },

    resetForm() {
      this.$refs.form.resetFields()
    },
    resetInForm() {
      this.$refs.form1.resetFields()
    },

    mod(row) {
      console.log(row)

      this.centerDialogVisible = true
      this.form = { ...row }
    },
    del(id) {
      console.log(id)
      this.$axios
        .get(this.$httpUrl + '/works/del?id=' + id)
        .then((res) => res.data)
        .then((res) => {
          console.log(res)
          if (res.code == 200) {
            //alert('添加成功')
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.loadPost()
          } else {
            //alert('添加失败')
            this.$message({
              message: '删除失败',
              type: 'error'
            })
          }
        })
    },

    loadCourse() {
      this.$axios
        .get(this.$httpUrl + '/course/list')
        .then((res) => res.data)
        .then((res) => {
          console.log(res)
          if (res.code == 200) {
            this.courseData = res.data
          } else {
            alert('获取数据失败')
          }
        })
    },
    loadWorkstype() {
      this.$axios
        .get(this.$httpUrl + '/workstype/list')
        .then((res) => res.data)
        .then((res) => {
          console.log(res)
          if (res.code == 200) {
            this.workstypeData = res.data
          } else {
            alert('获取数据失败')
          }
        })
    },
    formatCourse(row) {
      let temp = this.courseData.find((item) => {
        return item.id == row.course
      })
      return temp && temp.name
    },
    formatWorkstype(row) {
      let temp = this.workstypeData.find((item) => {
        return item.id == row.workstype
      })
      return temp && temp.name
    },
    selectCurrentChange(val) {
      this.currentRow = val
    },
    doInWorks() {
      this.$axios
        .post(this.$httpUrl + '/record/save', this.form1)
        .then((res) => res.data)
        .then((res) => {
          console.log(res)
          if (res.code == 200) {
            //alert('添加成功')
            this.$message({
              message: '操作成功',
              type: 'success'
            })
            this.inDialogVisible = false
            this.loadPost()
            this.resetInForm()
          } else {
            //alert('添加失败')
            this.$message({
              message: '操作失败',
              type: 'error'
            })
          }
        })
    },
    selectUser() {
      this.innerVisible = true
    },
    confirmUser() {
      this.form1.username = this.tempUser.name
      this.form1.userid = this.tempUser.id
      this.innerVisible = false
    },
    doSelectUser(val) {
      console.log(val)
      this.tempUser = val
    },

    handleSuccess(res) {
      console.log(res)
      this.loadPost()
    },
    handleError(err) {
      console.log(err)
      this.$message.error(err.msg)
    },
    handleRemove(file) {
      this.$axios.get(this.$httpUrl + '/file/delete', {
        params: {
          id: file.id
        }
      })
    },
    //测试
    beforeUpload(file) {
      const isPDF = file.type === 'application/pdf'
      if (!isPDF) {
        this.$message.error('只能上传 PDF 格式的文件')
      }
      return isPDF
    },
    handleChange(file, fileList) {
      console.log(fileList)
      console.log(file)
    },

    handleScore(row) {
      this.scoreShow = true
      this.form = { ...row }
    },

    saveScore() {
      this.$refs.socreForm.validate((valid) => {
        if (!valid) {
          return
        }
        this.doSave()
      })
    },

    closeScore() {
      this.form = {}
      this.scoreShow = false
    }
  }
}
</script>

<style scoped></style>
