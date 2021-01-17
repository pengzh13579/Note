[TOC]
## 1 父组件向子组件传值
- 父组件调用子组件的时候 绑定动态属性
`<v-header :title="title"></v-header>`
- 在子组件里面通过 props接收父组件传过来的数据
`props:['title']`
- 直接在子组件里面使用
`<h2>我是头部组件--{{title}}</h2>`
## 2 子组件主动获取父组件的数据和方法
this.$parent.数据
this.$parent.方法
## 3 非父子组件传值
- 定义VueEvent
```
import Vue from 'vue';
var VueEvent = new Vue();
export default VueEvent;
```
- 引入VueEvent
`import VueEvent from '../model/VueEvent.js';`
- 广播
`VueEvent.$emit('to-home',this.msg)`
- 接收
```
VueEvent.$on('to-home',function(data){
	console.log(data);
});
data即为msg的值
```
## 例一
父组件
```
<v-header :title="title" :homemsg='msg'  :run="run"  :home="this"></v-header>

<script>
import Header from './子组件.vue';
export default{
    data(){
		return {               
			msg:'我是一个父组件',
			title:'首页111'
		}
	},
    components:{
		'v-header':Header
	},
	methods:{
		run(data){
			alert('我是父组件的run方法'+data);
		}
	}
}
</script>
```
子组件
```
<h2>我是头部组件--{{title}}---{{homemsg}}</h2>
<button @click="run('123')">执行父组件的方法</button>
<button @click="getParent()">获取父组件的数据和方法</button>

<script>
export default{
    data(){
        return{
            msg:'子组件的msg'
        }
    },
    methods:{
        getParent(){
            // alert(this.title)
            // alert(this.home.title)
            this.home.run()
        }
    },
    props:['title','homemsg','run','home']
}
</script>
```
```
// https://cn.vuejs.org/v2/guide/components.html#Prop-验证
// 验证title必须是String类型
props:{
	'title':String
}
```

## 例二
父组件
```
<v-header ref="header"></v-header>
<button @click="getChildData()">获取子组件的数据和方法</button>

<script>
import Header from './Header.vue';
export default{
    data(){
        return {               
            msg:'我是一个home组件',
            title:'首页111'
        }
    },
    components:{
    	'v-header':Header
    },
    methods:{
        run(){
        	alert('我是Home组件的run方法');
        },
		getChildData(){
            // 父组件主动获取子组件的数据和方法：
            // alert(this.$refs.header.msg);
			this.$refs.header.run();
		}
	}
}
</script>
```
子组件
```
<button @click="getParentData()">获取子组件的数据和方法</button>
<script>
export default{
	data(){
        return{
            msg:'子组件的msg'
        }
    },
    methods:{
		run(){
			alert('我是子组件的run方法')
		},
		getParentData(){
            // alert(this.$parent.msg);
            //this.$parent.run();
        }
    }
}
</script>
```
