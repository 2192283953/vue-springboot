<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入作业名" suffix-icon="el-icon-search" style="width: 190px"
                @keyup.enter.n.native="loadPost"></el-input>
      <el-select v-model="course" placeholder="请选择课程" style="margin-left: 5px">
        <el-option
            v-for="item in courseData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="workstype" placeholder="请选择分类" style="margin-left: 5px">
        <el-option
            v-for="item in workstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="info" @click="resetParam">重置</el-button>
<!--      <el-button type="danger">新增</el-button>-->
    </div>
    <el-table :data="tableData" :header-cell-style="{background:'rgba(43,65,134,0.41)',color:'rgba(72,35,35,0.61)'}"
              border
              :cell-style="{borderColor:'rgba(35,36,38,0.97)'}"
    >
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="worksname" label="作业名" width="180">
      </el-table-column>
      <el-table-column prop="coursename" label="课程">
      </el-table-column>
      <el-table-column prop="workstypename" label="作业分类">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人">
      </el-table-column>
      <el-table-column prop="username" label="申请人">
      </el-table-column>
      <el-table-column prop="count" label="数量">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间">
      </el-table-column>
<!--      <el-table-column prop="remark" label="备注">-->
<!--      </el-table-column>-->

    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>


  </div>
</template>

<script>
export default {
  name: "RecordManege",
  data() {
    return {
      workstypeData:[],
      courseData:[],
      tableData: [],
      workstype:'',
      course:'',
      pageNum: 1,
      pageSize: 5,
      total: 0,
      name: '',

      centerDialogVisible: false,
      form: {
        id: '',
        name:'',
        remark:'',
        course:'',
        workstype:'',
        count:'',
        createtime:''

      },
    }
  },
  methods: {

    //post请求
    loadPost() {
      this.$axios.post(this.$httpUrl + '/record/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          workstype: this.workstype+'',
          course: this.course+''
        }
      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.tableData = res.data
          this.total = res.total
        } else {
          alert("获取数据失败")
        }

      })
    },
    //分页方法
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      //this.pageNum=1 防止前端更改页数的时候报错倒是可加可不加
      this.pageSize = val;
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost()
    },
    resetParam() {
      this.name = ''
      this.course=''
      this.workstype=''
    },

    resetForm() {
      this.$refs.form.resetFields();
    },

    loadCourse(){
      this.$axios.get(this.$httpUrl + '/course/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.courseData = res.data
        } else {
          alert("获取数据失败")
        }

      })
    },
    loadWorkstype(){
      this.$axios.get(this.$httpUrl + '/workstype/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.workstypeData = res.data
        } else {
          alert("获取数据失败")
        }

      })
    },

    formatCourse(row){
      let temp =this.courseData.find(item=>{
        return item.id==row.course
      })
      return temp && temp.name
    },
    formatWorkstype(row){
      let temp =this.workstypeData.find(item=>{
        return item.id==row.workstype
      })
      return temp && temp.name
    }

  }, //methods方法到这里截至
  beforeMount() {
    this.loadCourse()
    this.loadWorkstype()
    this.loadPost()
  }
}
</script>

<style scoped>

</style>