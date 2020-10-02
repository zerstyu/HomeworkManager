<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="과제 제목"
                                type="gradient-red"
                                v-bind:sub-title="subjectName + ' > ' + noticeTitle"
                                icon="ni ni-air-baloon"
                                class="mb-4 mb-xl-0"
                    >

                        <template slot="footer">
                            <!--span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 제출자</span>
                            <span class="text-nowrap">2020.01.11~2020.01.17</span-->
                        </template>
                    </stats-card>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="제출자"
                                type="gradient-red"
                                v-bind:sub-title="userName + ''"
                                icon="ni ni-circle-08"
                                class="mb-4 mb-xl-0"
                    >

                        <template slot="footer">
                            <!-- span class="text-success mr-2"><i class="fa fa-arrow-up"></i> </span-->
                            <!--span class="text-nowrap">1학년 7반</span-->
                        </template>
                    </stats-card>
                </div>
            </div>
        </base-header>


        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col">
                    <div class="card shadow">
                        <div class="card-header bg-transparent">
                            <h3 class="mb-0">채점하기</h3>
                        </div>
                        <div class="card-body">
                            <div class="row icon-examples">

                                <!--base-button type="primary">
                                    <span>80점</span>
                                    <badge type="white">만점 : 100</badge>
                                </base-button-->
                                <div class="col-md-12">
                                <h2><i class="ni ni-ruler-pencil"></i> 채점</h2>
                                    <div v-for="index in paginationTotal" :key="index">
                                    <iframe id="homeworkCanvasIframe" name="homeworkCanvasIframe" style="display: none;width:100%;height:870px;margin-top:-50px;" scrolling="no" src="canvas_resource/canvas.html" v-on:load="canvasOnLoad()">
                                    </iframe>
                                    </div>
                                </div>

                                <div>
                                    <base-pagination align="center" :page-count="paginationTotal" v-model="paginationDefault"></base-pagination>
                                    <br/>
                                    <br/>
                                </div>

                                <br/>
                                <br/>
                                <br/>
                                <br/>

                                <!-- 폼 -->
                                <form style="width:100%;">
                                    <div class="col-md-12">
                                        <h2><i class="ni ni-chat-round"></i> 피드백</h2>
                                        <textarea style="width:100%;" class="form-control" rows="3" placeholder="피드백을 남겨주세요."
                                          v-model="feedback"></textarea>
                                    </div>

                                    <br/>
                                    <br/>

                                    <div class="col-md-3">
                                        <h2><i class="ni ni-paper-diploma"></i> 평가</h2>
                                        <span class="text-nowrap">※ 이 과제의 최대 점수는 100점 입니다.</span>
                                        <br/>
                                        <br/>

                                        <base-input v-model="score" placeholder="점수"></base-input>
                                    </div>

                                    <br/>
                                    <br/>

                                    <div class="col-md-3">
                                        <base-button type="primary" icon="ni ni-bag-17" @click="homeworkSubmit()">평가 제출</base-button>

                                        <!--base-button type="info" icon="ni ni-bag-17" @click="getHistoryData(0)">히스토리 불러보기</base-button-->

                                    </div>

                                </form>
                                <br/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <modal :show.sync="modals">
            <template slot="header">
                <h5 class="modal-title" id="exampleModalLabel">알림</h5>
            </template>
            <div>
                {{responseMsg}}
            </div>
            <template slot="footer">
                <base-button type="secondary" @click="modals = false">확인</base-button>
                <!--base-button type="primary">Save changes</base-button-->
            </template>
        </modal>

        <modal :show.sync="modals2">
            <template slot="header">
                <h5 class="modal-title" id="exampleModalLabel2">알림</h5>
            </template>
            <div>
                채점이 완료되었습니다.
            </div>
            <template slot="footer">
                <base-button type="secondary" @click="goNoti()">확인</base-button>
                <!--base-button type="primary">Save changes</base-button-->
            </template>
        </modal>

    </div>
</template>
<script>
    import Vue from 'vue'
    import VueClipboard from 'vue-clipboard2'
    import BTooltipDirective from 'bootstrap-vue/esm/directives/tooltip'

    const axios = require('axios');

    Vue.use(VueClipboard)
    export default {
        directives: {
            'b-tooltip': BTooltipDirective
        },
        data() {
            return {
                homeworkCanvasData : [],
                icons: [
                    { name: "ni ni-air-baloon" }
                ],
                //pagination: {
                //    default: 1,
                //    total: 5
                //},
                paginationDefault: 1,
                paginationTotal: 5,

                isAssignMaster: '',

                assignmentFileList: [],
                assignmentId: '',
                feedback: '',
                isOpen: false,
                note: '',
                score: '',
                userName: '',//과제 제출학생의 네임값
                userId: '',//과제 제출학생의 아이디값
                subjectId: '',
                subjectName: '',
                noticeId: '',
                noticeTitle: '',

                modals: false,
                modals1: false,
                modals2: false,
                modals3: false,
                modals4: false,
                responseMsg: ''
            }
        },
        watch: {
            paginationDefault(newVal) {
                console.log("watch " + newVal);
                this.movePage(newVal);
            }
        },
        created() {
            this.getAssignment();
        },
        mounted() {
            this.homeworkCanvasData.push(
                {
                    //id : 1,
                    //page : 1,
                    //bgImg : '',
                    //data : '',

                    assignmentFileId : 1,
                    type : 'ORIGINAL',
                    fileString : '',
                    historyFileString : ''
                });
            //this.sendCanvas(this.homeworkCanvasData[0]);
        },
        methods: {
            goNoti(){
                this.modals2 = false;
                location.href = "/#/notice/" + this.noticeId;
            },
            movePage(page){
                console.log("movePage : " + page);
                let canvasIframe = document.getElementsByName("homeworkCanvasIframe");
                for(let i = 0; i < this.paginationTotal; i++){
                    if(i == (page - 1)){
                        canvasIframe[i].style.display = 'block';
                    }
                    else{
                        canvasIframe[i].style.display = 'none';
                    }
                }
            },
            getAssignment(){
                let vm = this;

                //로그인 유효성 체크
                if(localStorage.getItem('userId') == '' ||
                    localStorage.getItem('userId') == null){
                    this.responseMsg = "로그인이 필요한 서비스 입니다. 로그인 후 다시 시도해 주세요!";
                    this.modals = true;
                    return;
                }

                axios.get('/api/assignments/' + this.$route.params.assignId)
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){
                            vm.assignmentFileList = response.data.data.assignmentFileList;
                            vm.assignmentId = response.data.data.assignmentId;
                            vm.feedback = response.data.data.feedback;
                            vm.isOpen = response.data.data.isOpen;
                            vm.note = response.data.data.note;
                            vm.score = response.data.data.score;
                            vm.userId = response.data.data.userId;
                            vm.subjectId = response.data.data.subjectId;
                            vm.subjectName = response.data.data.subjectName;
                            vm.noticeId = response.data.data.noticeId;
                            vm.noticeTitle = response.data.data.noticeTitle;

                            if(vm.assignmentId == localStorage.getItem('userId')){
                                vm.isAssignMaster = true;
                            }

                            vm.paginationTotal = response.data.data.assignmentFileList.length;
                            if(vm.paginationTotal > 0){
                                vm.movePage(1);
                            }

                            vm.canvasOnLoad();

                            //for(let i = 0; i < vm.assignmentFileList.length; i++){

                            //}

                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            },



            onCopy() {
                this.$notify({
                    type: 'success',
                    title: 'Copied to clipboard'
                })
            },
            sendCanvas(canvasData, idx) {
                //alert(canvasData);
                let canvasIframe = document.getElementsByName("homeworkCanvasIframe")[idx];
                let responData = canvasIframe.contentWindow.loadData(canvasData, localStorage.getItem('userName'));//
                //let responData = canvasIframe.contentWindow.loadData(this.assignmentFileList[0]);
                console.log("응답입니다 " + responData);
            },
            getHistoryData(idx){
                let canvasIframe = document.getElementsByName("homeworkCanvasIframe")[idx];
                let responData = canvasIframe.contentWindow.getHistoryData();

                this.assignmentFileList[idx].historyFileString = responData;
                console.log("히스토리 내역입니다" + responData);
                return responData;
            },
            canvasOnLoad(){
                //this.sendCanvas(this.assignmentFileList[0]);
                //let canvasIframe = document.getElementsByName("homeworkCanvasIframe");
                for(let i = 0; i < this.paginationTotal; i++){
                    this.sendCanvas(this.assignmentFileList[i], i);
                }
            },







            homeworkSubmit(){
                let vm = this;
                const axiosConfig = { headers:{ "Content-Type": "application/json"} };

                axios.put('/api/assignments/' + this.$route.params.assignId,
                    '{' +
                    '"feedback": "' + vm.feedback.replace(/(?:\r\n|\r|\n)/g, '<br/>') + '",' +
                    '"noticeId": "' + vm.noticeId + '",' +
                    '"score": "' + vm.score + '",' +
                    '"subjectId": "' + vm.subjectId + '",' +
                    '"userId": "' + vm.userId + '"' +
                    '}'
                    , axiosConfig)
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){

                            vm.homeworkFileSubmit(response.data.data.id);
                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            },
            homeworkFileSubmit(assId){
                let vm = this;
                let base64Str = '';
                let base64Flag = false;

                for(let i = 0; i < this.assignmentFileList.length; i++){
                    base64Flag = true;
                    base64Str += '{ "assignmentId" : "' + assId + '",';//assignmentFileId
                    base64Str += ' "assignmentFileId" : "' + this.assignmentFileList[i].assignmentFileId + '",';//assignmentFileId
                    base64Str += '  "historyFileString" : "' + this.getHistoryData(i) + '",';
                    base64Str += '  "type" : "ORIGINAL",';
                    base64Str += '  "userId" : "' + vm.userId + '"},';
                }
                if(base64Flag == true){
                    base64Str = base64Str.substr(0, base64Str.length -1);
                }
                const axiosConfig = { headers:{ "Content-Type": "application/json"} };

                axios.put('/api/assignment_files/',
                    '[' + base64Str + ']'
                    , axiosConfig)
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){
                            vm.modals2 = true;
                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            }
        }
    };
/*

    function sendCanvas(canvasData){
        var canvasIframe = document.getElementById("homeworkCanvasIframe");
        canvasIframe.contentWindow.loadData(canvasData);
    }

 */
</script>
<style></style>
