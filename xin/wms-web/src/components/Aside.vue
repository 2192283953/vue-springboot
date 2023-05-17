<template>
  <el-menu
      background-color="#5F9EA0"
      text-color="#fff"
      active-text-color="#ffd04b"
      default-active="/Home"
      style="height: 100vh"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
  >
    <!--      isCollapse:代表是否折叠左侧搜索栏-->
    <!--  collapse-transition:代表是否取消动画-->

    <el-menu-item index="/Home">
      <i class="el-icon-house" style="color: darkorange"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon" style="color: darkorange"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>

<!--    <el-menu-item index="/User">-->
<!--      <i class="el-icon-house" style="color: #ff8c00"></i>-->
<!--      <span slot="title">导航二</span>-->
<!--    </el-menu-item>-->

<!--    <el-menu-item index="/Three">-->
<!--      <i class="el-icon-house" style="color: darkorange"></i>-->
<!--      <span slot="title">导航三</span>-->
<!--    </el-menu-item>-->

    <!--    <el-submenu index="1">-->
    <!--      <template slot="title"><i class="el-icon-message"></i>导航一</template>-->
    <!--      <el-menu-item-group>-->
    <!--        <template slot="title">分组一</template>-->
    <!--        <el-menu-item index="1-1">选项1</el-menu-item>-->
    <!--        <el-menu-item index="1-2">选项2</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-menu-item-group title="分组2">-->
    <!--        <el-menu-item index="1-3">选项3</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-submenu index="1-4">-->
    <!--        <template slot="title">选项4</template>-->
    <!--        <el-menu-item index="1-4-1">选项4-1</el-menu-item>-->
    <!--      </el-submenu>-->
    <!--    </el-submenu>-->
    <!--    <el-submenu index="2">-->
    <!--      <template slot="title"><i class="el-icon-menu"></i>导航二</template>-->
    <!--      <el-menu-item-group>-->
    <!--        <template slot="title">分组一</template>-->
    <!--        <el-menu-item index="2-1">选项1</el-menu-item>-->
    <!--        <el-menu-item index="2-2">选项2</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-menu-item-group title="分组2">-->
    <!--        <el-menu-item index="2-3">选项3</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-submenu index="2-4">-->
    <!--        <template slot="title">选项4</template>-->
    <!--        <el-menu-item index="2-4-1">选项4-1</el-menu-item>-->
    <!--      </el-submenu>-->
    <!--    </el-submenu>-->
    <!--    <el-submenu index="3">-->
    <!--      <template slot="title"><i class="el-icon-setting"></i>导航三</template>-->
    <!--      <el-menu-item-group>-->
    <!--        <template slot="title">分组一</template>-->
    <!--        <el-menu-item index="3-1">选项1</el-menu-item>-->
    <!--        <el-menu-item index="3-2">选项2</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-menu-item-group title="分组2">-->
    <!--        <el-menu-item index="3-3">选项3</el-menu-item>-->
    <!--      </el-menu-item-group>-->
    <!--      <el-submenu index="3-4">-->
    <!--        <template slot="title">选项4</template>-->
    <!--        <el-menu-item index="3-4-1">选项4-1</el-menu-item>-->
    <!--      </el-submenu>-->
    <!--    </el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      //isCollapse:false
      // menu:[
      //   {
      //     menuClick:'Admin',
      //     menuName:'管理员管理',
      //     menuIcon:'el-icon-s-custom'
      //   },{
      //     menuClick:'User',
      //     menuName:'用户管理',
      //     menuIcon:'el-icon-user-solid'
      //   }
      // ]
    }
  },
  computed:{
    "menu":{
        get(){
          return this.$store.state.menu
        }
    }
  },
  props: {
    isCollapse: Boolean
  }
}
</script>

<style scoped>

</style>