<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="통계자료"
                                type="gradient-red"
                                sub-title="나의 숙제 이력을 기반으로 분석된 결과입니다."
                                class="mb-4 mb-xl-0"
                    >
                        <template slot="footer">
                            <!--span class="text-info mr-2">{{userName}}</span-->
                            <!--span class="text-nowrap">{{userName}}님의 통계입니다.</span-->
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
                            <h3 class="mb-0">통계자료</h3>
                        </div>
                        <div class="card-body">
                            <div class="col-xl-12">
                                <card header-classes="bg-transparent">
                                    <div slot="header" class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-uppercase text-muted ls-1 mb-1">나의 통계</h6>
                                            <h5 class="h3 mb-0">카테고리별 평균점수</h5>
                                        </div>
                                    </div>

                                    <bar-chart
                                            :height="350"
                                            ref="barChart"
                                            :chart-data="redBarChart.chartData"
                                    >
                                    </bar-chart>
                                </card>
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
                <h5 class="modal-title" id="exampleModalLabel2">숙제방 만들기</h5>
            </template>
            <div>
                <base-input placeholder="숙제명을 입력해주세요" v-model="createSubjectName"></base-input>
            </div>
            <template slot="footer">
                <base-button type="primary">숙제방 생성</base-button>
                <base-button type="default" @click="modals2 = false">취소</base-button>
            </template>
        </modal>


    </div>
</template>
<script>
    import Vue from 'vue'
    import VueClipboard from 'vue-clipboard2'
    import BTooltipDirective from 'bootstrap-vue/esm/directives/tooltip'
    // Charts
    import * as chartConfigs from '../components/Charts/config';
    //import LineChart from '../components/Charts/LineChart';
    import BarChart from '../components/Charts/BarChart';

    const axios = require('axios');

    Vue.use(VueClipboard)
    export default {
        components : {
            //LineChart,
            BarChart
        },
        directives: {
            'b-tooltip': BTooltipDirective
        },
        data() {
            return {
                bigLineChart: {//bigLineChart.allData.allData
                    allData: [
                        []
                    ],
                    activeIndex: 0,
                    chartData: {
                        datasets: [],
                        labels: [],
                    },
                    extraOptions: chartConfigs.blueChartOptions,
                    labels: []
                },
                redBarChart: {
                    chartData: {
                        labels: ['학술>수학', '학술>영어', '컴퓨터>코딩'],
                        datasets: [{
                            label: '평균점수',
                            data: [340, 222, 495]
                        }]
                    }
                },

                userName: '',

                responseMsg: '',
                modals: false,
                modals2: false
            }
        },
        created() {
            this.getStaticsTotalScore();
        },
        methods: {
            changeRedChart(nameArr, scoreArr){
                let tmp = {
                    chartData: {
                        labels: nameArr,
                        datasets: [{
                            label: '총점',
                            data: scoreArr
                        }]
                    }
                }
                this.redBarChart = tmp;
            },
            getStaticsTotalScore(){
                let vm = this;
                axios.get('/api/statistics/subjects/category_averages?userId=' + localStorage.getItem('userId'))
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){
                            vm.redBarChart.chartData.labels = [];
                            vm.redBarChart.chartData.datasets[0].data = [];

                            vm.userName = response.data.data.userName;
                            let nameAr = [];
                            let scoreAr = [];

                            nameAr = response.data.data[0].categoryList;
                            scoreAr = response.data.data[0].averageList;

/*
                            for(let i = 0; i < response.data.data[0].statisticsDtoList.length; i++){
                                nameAr.push(response.data.data[0].statisticsDtoList[i].userName);
                                scoreAr.push(response.data.data[0].statisticsDtoList[i].totalScore);
                                //vm.redBarChart.chartData.labels.push(response.data.data[0].statisticsDtoList[i].userName);
                                //vm.redBarChart.chartData.datasets[0].data.push(response.data.data[0].statisticsDtoList[i].totalScore);
                            }

 */
                            console.log("통계데이터 수신완료");
                            console.log(nameAr);
                            vm.changeRedChart(nameAr, scoreAr);
                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            }
        }
    };
</script>
<style></style>
