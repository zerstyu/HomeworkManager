<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="제출 정보"
                                type="gradient-red"
                                sub-title="수학2 > 지수함수와 로그 그래프"
                                class="mb-4 mb-xl-0"
                    >

                        <template slot="footer">
                            <span class="text-info mr-2">선생님 정보 </span>
                            <span class="text-nowrap">추대윤</span>
                        </template>
                    </stats-card>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="제출자"
                                type="gradient-red"
                                sub-title="이태호"
                                class="mb-4 mb-xl-0"
                    >

                        <template slot="footer">
                            <!-- span class="text-success mr-2"><i class="fa fa-arrow-up"></i> </span-->
                            <span class="text-nowrap">안랩</span>
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
                            <h3 class="mb-0">채점결과</h3>
                        </div>
                        <div class="card-body">
                            <div class="row icon-examples" v-if="feedback == null">
                                <div class="col-md-12">
                                    <h2><i class="ni ni-ruler-pencil"></i> 채점이 진행중 입니다.</h2>
                                    <br/>
                                    <div class="col-md-12">
                                        <h2><i class="ni ni-chat-round"></i> 학생 노트</h2>
                                        <p v-html="note"></p>
                                    </div>
                                    <br/>
                                    <br/>
                                    <div class="col-md-12">
                                        <base-button type="default" icon="ni ni-bag-17" @click="modals3 = true">학생제출원본 확인</base-button>
                                    </div>
                                </div>
                            </div>

                            <div class="row icon-examples" v-if="feedback != null">

                                <!--base-button type="primary">
                                    <span>80점</span>
                                    <badge type="white">만점 : 100</badge>
                                </base-button-->
                                <div class="col-md-12">
                                <h2><i class="ni ni-ruler-pencil"></i> 채점</h2>
                                    <iframe id="homeworkCanvasIframe" style="width:100%;height:870px;margin-top:-50px;" scrolling="no" src="canvas_resource/canvas.html" v-on:load="canvasOnLoad()">
                                    </iframe> </div>

                                <div>
                                    <base-pagination :page-count="10" v-model="pagination.default"></base-pagination>
                                </div>

                                <br/>
                                <br/>
                                <br/>
                                <br/>

                                <!-- 폼 -->
                                <form style="width:100%;">
                                    <div class="col-md-12">
                                        <h2><i class="ni ni-chat-round"></i> 선생님의 피드백</h2>
                                        <p v-html="feedback"></p>
                                    </div>

                                    <br/>
                                    <br/>

                                    <div class="col-md-3">
                                        <h2><i class="ni ni-paper-diploma"></i> 점수</h2>
                                        <span class="text-nowrap">※ 이 과제의 최대 점수는 100점 입니다.</span>
                                        <h3>{{score}}점</h3>
                                    </div>

                                    <br/>
                                    <br/>
                                    <hr>
                                    <br/>
                                    <br/>
                                    <div class="col-md-12">
                                        <h2><i class="ni ni-chat-round"></i> 학생 노트</h2>
                                        <p v-html="note"></p>
                                    </div>
                                    <br/>
                                    <br/>
                                    <div class="col-md-12">
                                        <base-button type="default" icon="ni ni-bag-17" @click="modals3 = true">학생제출원본 확인</base-button>
                                    </div>
                                    <br/>
                                    <br/>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>


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
                <h5 class="modal-title" id="exampleModalLabel2">과제방 만들기</h5>
            </template>
            <div>
                <base-input placeholder="과제명을 입력해주세요" v-model="createSubjectName"></base-input>
            </div>
            <template slot="footer">
                <base-button type="primary">과제방 생성</base-button>
                <base-button type="default" @click="modals2 = false">취소</base-button>
            </template>
        </modal>

        <modal :show.sync="modals3">
            <template slot="header">
                <h5 class="modal-title" id="exampleModalLabel3">과제 원본</h5>
            </template>
            <div v-for="item in assignmentFileList" v-bind:key="item.id">
                <img v-bind:src="item.fileString" />
                <br/>
            </div>
            <template slot="footer">
                <base-button type="secondary" @click="modals3 = false">확인</base-button>
                <!--base-button type="primary">Save changes</base-button-->
            </template>
        </modal>

        <modal :show.sync="modals5">
            <template slot="header">
                <h5 class="modal-title" id="exampleModalLabel5">과제 제출</h5>
            </template>
            <div>

                <h4>과제 노트</h4>
                <textarea class="form-control" id="exampleFormControlTextarea111" rows="3" placeholder="완료한 과제에 대해 설명하세요"
                          v-model="createAssignNote"></textarea>
                <br/>

                <h4>과제자료 첨부</h4>
                <base-button size="sm" @click="createAssignFileMinus()" type="primary">-</base-button>
                {{notiEditFileLen}}
                <base-button size="sm" @click="createAssignFilePlus()" type="primary">+</base-button>
                <br/>
                <div v-for="index in createAssignmentFileLen" :key="index">
                    <input type="file" id="files2" name="createAssignFileInput" @change="getBase64_assign(index)"/>
                </div>
                <br/>

                <h4>과제 유형</h4>
                <base-radio name="OPEN" class="mb-5" v-model="createAssignIsOpen">
                    오픈과제
                </base-radio>
                <base-radio name="PRIVATE" class="mb-5" v-model="createAssignIsOpen">
                    프라이빗과제
                </base-radio>

            </div>
            <template slot="footer">
                <base-button type="primary" @click="homeworkSubmit()">과제 제출</base-button>
                <base-button type="default" @click="modals5 = false">취소</base-button>
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
                pagination: {
                    default: 1
                },

                isAssignMaster: '',

                assignmentFileList: [],
                assignmentId: '',
                feedback: '',
                isOpen: false,
                note: '',
                score: '',


                createAssignIsOpen: 'OPEN',
                createAssignNote: '',
                //createAssignNoticeId:
                createAssignScore: 0,
                //createAssignSubjectId
                //createAssignUserId
                createAssignmentFileDtoList: ["","","","","","","","","","",
                    "","","","","","","","","","",
                    "","","","","","","","","","",
                    "","","","","","","","","","",
                    "","","","","","","","","",""],
                createAssignmentFileLen: 1,


                responseMsg: '',
                modals: false,
                modals2: false,
                modals3: false,
                modals4: false,
                modals5: false
            }
        },
        mounted() {
            this.getAssignment();

            this.homeworkCanvasData.push(
                {
                    id : 1,
                    page : 1,
                    bgImg : 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/7QAkUGhvdG9za'
                });

            //this.sendCanvas(this.homeworkCanvasData[0]);
        },
        methods: {
            homeworkSubmit(){
                this.modals5 = false;
                let vm = this;
                let isOpenOrClose = false;
                if(this.createAssignIsOpen == 'OPEN') {
                    isOpenOrClose = true;
                }
                const axiosConfig = { headers:{ "Content-Type": "application/json"} };

                axios.put('/api/assignments/' + this.$route.params.assignId,//
                    '{' +
                    '"note": "' + vm.createAssignNote.replace(/(?:\r\n|\r|\n)/g, '<br/>') + '",' +
                    '"isOpen": "' + isOpenOrClose + '",' +
                    '"noticeId": "2",' +
                    '"score": "0",' +
                    '"subjectId": "1",' +
                    '"userId": "' + localStorage.getItem('userId') + '"' +
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

                for(let i = 1; i <= this.createAssignmentFileLen; i++){
                    base64Flag = true;
                    base64Str += '{ "assignmentId" : "' + assId + '",';
                    base64Str += '  "fileString" : "' + this.createAssignmentFileDtoList[i] + '",';
                    base64Str += '  "type" : "ORIGINAL",';
                    base64Str += '  "userId" : "' + localStorage.getItem('userId') + '"},';
                }
                if(base64Flag == true){
                    base64Str = base64Str.substr(0, base64Str.length -1);
                }
                const axiosConfig = { headers:{ "Content-Type": "application/json"} };

                axios.put('/api/assignment_files/' + assId,
                    '[' + base64Str + ']'
                    , axiosConfig)
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){
                            vm.modals = true;
                            vm.responseMsg = '성공하였습니다.';
                            vm.getAssignments();
                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            },
            getBase64_assign(idx) {
                console.log("base64_1 변환 요청 idx : " + idx);
                let file = document.getElementsByName("createAssignFileInput")[idx-1].files[0];
                let idx2 = idx;
                let vm = this;


                let reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function () {
                    console.log("success!! base64_assign : " + reader.result);
                    vm.createAssignmentFileDtoList[idx2] = reader.result;
                };
                reader.onerror = function (error) {
                    console.log('base 64 Error.... : ', error);
                };
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

                            if(vm.assignmentId == localStorage.getItem('userId')){
                                vm.isAssignMaster = true;
                            }

                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            },





            ////////////////////
            onCopy() {
                this.$notify({
                    type: 'success',
                    title: 'Copied to clipboard'
                })
            },
            sendCanvas(canvasData) {
                //alert(canvasData);
                let canvasIframe = document.getElementById("homeworkCanvasIframe");
                canvasIframe.contentWindow.loadData(canvasData);
            },
            canvasOnLoad(){
                this.sendCanvas(this.homeworkCanvasData[0]);
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
