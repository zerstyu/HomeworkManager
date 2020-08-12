<template>
  <div class="card shadow"
       :class="type === 'dark' ? 'bg-default': ''">
    <div class="card-header border-0"
         :class="type === 'dark' ? 'bg-transparent': ''">
      <div class="row align-items-center">
        <div class="col">
          <h3 class="mb-0" :class="type === 'dark' ? 'text-white': ''">
            {{title}}
          </h3>
        </div>
        <div class="col text-right">
          <!--base-button type="primary" size="sm" @click="clickChangeSunjectInfoButton()" v-if="subjectMasterId==nowUser">과목 정보수정</base-button>
          <base-button type="primary" size="sm" v-if="subjectMasterId==nowUser">과제 추가</base-button-->
        </div>
      </div>
    </div>

    <div class="table-responsive" id="notiTable">
      <base-table class="table align-items-center table-flush"
                  :class="type === 'dark' ? 'table-dark': ''"
                  :thead-classes="type === 'dark' ? 'thead-dark': 'thead-light'"
                  tbody-classes="list"
                  :data="assignmentsData">
        <template slot="columns">
          <th>제출자</th>
          <th>제출타입</th>
          <th>점수</th>
          <th>제출날짜</th>
          <th>확인</th>
        </template>

        <template slot-scope="{row}">
          <th scope="row">
            <div class="media align-items-center">
              <div class="media-body">
                <span class="name mb-0 text-sm">{{row.userName}}</span>
              </div>
            </div>
          </th>

          <td v-if="row.isOpen==true">
            <badge class="badge-dot mr-4" type="info">
              <span class="status">공개</span>
            </badge>
          </td>
          <td v-else>
            <badge class="badge-dot mr-4" type="danger">
              <span class="status">비공개</span>
            </badge>
          </td>

          <td class="budget" v-if="row.score == null">
            채점중
          </td>
          <td class="budget" v-else>
            {{row.score}}
          </td>

          <td class="budget">
            {{row.createdAt}}
          </td>

          <td class="budget">
            <base-button type="info" size="sm" @click="clickAssignmentDetailButton(row.id)">상세보기</base-button>
            <base-button type="info" size="sm" v-if="isNotiMaster==true" @click="clickConfirmButton(row.id)">채점하기</base-button>
          </td>

        </template>

      </base-table>
    </div>

    <div class="card-footer d-flex justify-content-end"
         :class="type === 'dark' ? 'bg-transparent': ''">
      <base-pagination total="30"></base-pagination>
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

  </div>
</template>
<script>
  import {BUS} from "../EventBus";

  const axios = require('axios');

  export default {
    name: 'projects-table',
    props: {
      type: {
        type: String
      },
      assignmentsData: Array,
      isNotiMaster: Array
    },
    data() {
      return {
        modals: false,
        modals3: false,
        responseMsg: false,
        tableData: [
          {
            img: 'img/theme/bootstrap.jpg',
            title: '2차 함수 응용',
            budget: '50점',
            status: '평가진행중',
            statusType: 'warning',
            completion: 60
          },
          {
            img: 'img/theme/angular.jpg',
            title: '2차 방정식',
            budget: '40점',
            status: '평가완료',
            statusType: 'success',
            completion: 100
          },
          {
            img: 'img/theme/sketch.jpg',
            title: '1차 수행평가',
            budget: '100점',
            status: '연기',
            statusType: 'danger',
            completion: 72
          },
          {
            img: 'img/theme/react.jpg',
            title: '지수함수와 로그',
            budget: '20',
            status: '진행 중',
            statusType: 'info',
            completion: 90
          },
          {
            img: 'img/theme/vue.jpg',
            title: '행렬의 곱',
            budget: '10',
            status: '평가완료',
            statusType: 'success',
            completion: 100
          }
        ]
      }
    },
    methods : {
      clickAssignmentDetailButton(id){
        BUS.$emit('assignmentDetail', id);
        console.log("이벤트버스: BUS.$emit('assignmentDetail', true);");
      },
      clickConfirmButton(id){
        BUS.$emit('confirm', id);
        console.log("이벤트버스: BUS.$emit('confirm', true);");
      },
      updateSubjectReq(){
        let vm = this;
        const axiosConfig = { headers:{ "Content-Type": "application/json"} };

        axios.put('/api/subjects',
                '{' +
                '"subjectName": "' + vm.subjectPivotNameEdit + '",' +
                '"subjectId": "' + vm.subjectId + '"' +
                '}'
                //vm.createSubjectName
                //form
                , axiosConfig)
                .then(function(response){
                  if(response.data.statusCode == 'OK'){
                    vm.modals3 = false;
                    vm.modals = true;
                    vm.responseMsg = '성공하였습니다.';
                    BUS.$emit('subjectUpdate', '');
                  }
                  else{
                    vm.modals = true;
                    vm.responseMsg = response.data.message;
                  }
                });
      },
      deleteSubjectReq(){
        alert("준비중 입니다.");
      }
    }
  }
</script>
<style>
</style>
