<template>
    <div class="app-container calendar-list-container">
        <!--条件查询-->
        <div class="filter-container">
            <el-input  style="width: 130px;" class="filter-item" v-model="query.name" :placeholder="'组织名称'">
            </el-input>
            <el-input  style="width: 130px;" class="filter-item" v-model="query.pid" :placeholder="'上级组织id'">
            </el-input>
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" v-waves icon="el-icon-search" @click="getList">{{$t('table.search')}}</el-button>
            <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('table.add')}}</el-button>
            <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download"  v-loading="downloadLoading" element-loading-text="正在下载中" @click="handleDownload">{{$t('table.export')}}</el-button>
            <el-button class="filter-item" style="margin-left: 10px;" @click="handleDelete" type="danger" icon="el-icon-delete">{{$t('table.delete')}}</el-button>
        </div>
        <!--带边框的表格-->
        <el-table
                :data="tableData"
                border
                v-loading="listLoading" element-loading-text="拼命加载中"
                @selection-change="handleSelectionChange"
                style="width: 100%">
            <!--展开显示详情-->
            <el-table-column
                    type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="组织id">
                            <span>{{ props.row.id }}</span>
                        </el-form-item>
                        <el-form-item label="组织名称">
                            <span>{{ props.row.name }}</span>
                        </el-form-item>
                        <el-form-item label="组织描述">
                            <span>{{ props.row.description }}</span>
                        </el-form-item>
                        <el-form-item label="上级组织id">
                            <span>{{ props.row.pid }}</span>
                        </el-form-item>
                        <el-form-item label="创建时间">
                            <!--时间戳转换-->
                            <span>{{props.row.ctime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <!--多选支持-->
            <el-table-column
                    type="selection"
            >
            </el-table-column>
            <!--自定义索引-->
            <el-table-column
                    type="index"
                    label="序号"
                    align="center"
                    :index="indexMethod">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="id"
                    label="组织id"
            >
            </el-table-column>
            <el-table-column
                    sortable
                    prop="name"
                    label="组织名称"
            >
            </el-table-column>
            <el-table-column
                    sortable
                    prop="pid"
                    label="上级组织id"
            >
            </el-table-column>
            <!--操作按钮-->
            <el-table-column align="center" :label="$t('table.actions')" width="230" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <!--编辑-->
                    <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('table.edit')}}</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--分页组件-->
        <div class="pagination-container">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="current_page"
                    :page-sizes="page_sizes"
                    :page-size="page_size"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total_size">
            </el-pagination>
        </div>
        <!--添加修改组织-->
        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
            <el-form :rules="rules" ref="info" :model="info" label-position="right" label-width="100px" style='width: 400px; margin-left:50px;'>
                <!--prop用于进行表单校验-->
                <el-form-item label="组织名称" prop="name">
                    <el-input v-model="info.name"></el-input>
                </el-form-item>
                <el-form-item label="组织描述" prop="description">
                    <el-input v-model="info.description"></el-input>
                </el-form-item>
                <el-form-item label="上级组织id" prop="pid">
                    <el-input v-model="info.pid"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">{{$t('table.cancel')}}</el-button>
                <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{$t('table.confirm')}}</el-button>
                <el-button v-else type="primary" @click="updateData">{{$t('table.confirm')}}</el-button>
            </div>
        </el-dialog>
    </div>
</template>


<script>
    import waves from '@/directive/waves' // 水波纹指令
    import { parseTime } from '@/utils'
    import { add, getByCondition, deleteByIds, updateSelectiveById } from '@/api/upms/organization'
    export default{
        directives: {
            waves
        },
        data() {
            return {
                data: [{
                    id: 1,
                    label: 'micro-fast项目部',
                    children: [{
                        id: 4,
                        label: 'upms系统开发小队',
                        children: [{
                            id: 9,
                            label: 'upms后端团队'
                        }, {
                            id: 10,
                            label: 'upms前端团队'
                        }]
                    },
                        {
                            id: 5,
                            label: 'oauth系统开发小队',
                            children: [{
                                id: 6,
                                label: 'oauth后端团队'
                            }, {
                                id: 11,
                                label: 'oauth前端团队'
                            }]
                        },
                        {
                            id: 12,
                            label: 'ucenter系统开发小队',
                            children: [{
                                id: 14,
                                label: 'ucenter后端团队'
                            }, {
                                id: 16,
                                label: 'ucenter前端团队'
                            }]
                        }]
                }],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                tableData: null, // 组织查询的数据
                current_page: 1,
                page_sizes: [5, 10, 15, 20, 50],
                page_size: 5,
                total_size: undefined,
                listLoading: false,
                status: [ // 描述状态
                    0,
                    1
                ],
                info: {
                    id: undefined, // 用于添加组织
                    name: undefined,
                    pid: undefined,
                    description: undefined
                },
                query: { // 用于查询组织
                    name: undefined,
                    pid: undefined
                },
                dialogFormVisible: false, // 添加更新组织的会话窗口
                dialogStatus: undefined, // 会话的状态,是创建还是修改
                textMap: {
                    update: '编辑组织',
                    create: '添加组织'
                },
                downloadLoading: false,
                multipleSelection: [],
                rules: {
                    name: [
                        { required: true, message: '请输入组织名称', trigger: 'blur' }
                    ],
                    pid: [
                        { required: true, message: '请输入上级组织id', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: '请输入组织描述', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            deepClon(obj) { // 深度克隆对象
                const proto = Object.getPrototypeOf(obj)
                return Object.assign({}, Object.create(proto), obj)
            },
            indexMethod(index) { // 自定义序号
                return this.page_size * (this.current_page - 1) + index + 1
            },
            /**
             * 懒加载树的方法
             * @param node
             * @param resolve
             */
            loadData(node, resolve) {
                if (node.level === 0) {
                    resolve([{
                        id: 1,
                        label: 'micro-fast项目部'
                    }])
                }
                if (node.level > 0) {
                    resolve([])
                }
            },
            handleCreate() {
                // 初始化数据
                for (var item in this.info) {
                    this.info[item] = undefined
                }
                // 清除表单校验结果
                if (this.$refs['info']) {
                    this.$refs['info'].clearValidate()
                }
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
            },
            createData() {
                this.$refs.info.validate((validate) => {
                    if (validate) {
                        add(this.info).then(response => {
                            if (response.data.code === 0) {
                                this.dialogFormVisible = false
                                this.$message({
                                    message: '添加成功',
                                    type: 'success'
                                })
                                this.getList()
                            } else {
                                this.$message({
                                    message: '添加失败',
                                    type: 'error'
                                })
                            }
                        })
                    }
                })
            },
            handleUpdate(value) { // 更新
                if (this.$refs['info']) {
                    this.$refs['info'].clearValidate()
                }
                // 一定要深度克隆对象,不然容易出bug.比如先编辑然后添加
                this.info = this.deepClon(value)
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
            },
            updateData() {
                this.$refs['info'].validate((validate) => {
                    if (validate) {
                        // 更新数据
                        updateSelectiveById(this.info).then(response => {
                            if (response.data.code === 0) {
                                this.dialogFormVisible = false
                                this.$message({
                                    message: '更新信息成功',
                                    type: 'success'
                                })
                                this.getList()
                            } else {
                                this.$message({
                                    message: '更新信息失败',
                                    type: 'error'
                                })
                            }
                        })
                    } else {
                        this.$message({
                            message: '请按提示填入正确的表单信息',
                            type: 'info'
                        })
                    }
                })
            },
            handleDelete() {
                this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.multipleSelection.length < 1) {
                        this.$message({
                            message: '请选择要删除的组织',
                            type: 'info'
                        })
                    }
                    // 传入数组
                    const ids = []
                    const data = this.multipleSelection
                    data.forEach((item) => {
                        ids.push(item.id)
                    })
                    this.deleteData(ids).then(response => {
                        if (response.data.code === 0) {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            })
                        } else {
                            this.$message({
                                type: 'error',
                                message: '删除失败!'
                            })
                        }
                    })
                })
            },
            deleteData(ids) {
                // 删除系统
                deleteByIds(ids).then(response => {
                    if (response.data.code === 0) {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        })
                        this.getList()
                    } else {
                        this.$message({
                            message: '删除失败',
                            type: 'error'
                        })
                    }
                })
            },
            getList() {
                this.listLoading = true
                // 加载列表
                getByCondition(this.query, this.current_page, this.page_size, 'pid_asc').then(response => {
                    this.listLoading = false
                    if (response.data.code === 0) {
                        const data = response.data.data
                        this.tableData = data.list
                        this.total_size = data.total
                    } else {
                        this.$message({
                            message: '加载信息失败',
                            type: 'error'
                        })
                    }
                })
            },
            handleSizeChange(pageSize) {
                if (pageSize === this.page_size) {
                    return
                }
                this.page_size = pageSize
                this.getList()
            },
            handleCurrentChange(pageNum) {
                if (pageNum === this.current_page) {
                    return
                }
                this.current_page = pageNum
                this.getList()
            },
            handleSelectionChange(value) {
                this.multipleSelection = value
            },
            handleDownload() { // 导出当前页数据到excel
                this.downloadLoading = true
                import('@/vendor/Export2Excel').then(excel => {
                    const tHeader = ['组织id', '组织名称', '组织描述', '创建时间', '上级组织id']
                    const filterVal = ['id', 'name', 'description', 'ctime', 'pid']
                    const data = this.formatJson(filterVal, this.multipleSelection)
                    excel.export_json_to_excel(tHeader, data, 'upms-organization')
                    this.downloadLoading = false
                })
            },
            formatJson(filterVal, jsonData) { // 格式化时间
                return jsonData.map(v => filterVal.map(j => {
                    if (j === 'ctime') {
                        return parseTime(v[j])
                    } else {
                        return v[j]
                    }
                }))
            }
        },
        created() {
            this.getList()
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .tree-container{
        border: 1px dashed #85bbd2;
        border-radius: 2px;
        padding: 10px;
        text-align: center;
        min-height: 500px;
    .tree-title{
        display: inline-block;
        height: 40px;
        padding: 10px;
        color: #999999;
    }
    }
    .demo-table-expand {
        font-size: 0;
    label {
        width: 90px;
        color: #99a9bf;
    }
    .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    }
</style>
