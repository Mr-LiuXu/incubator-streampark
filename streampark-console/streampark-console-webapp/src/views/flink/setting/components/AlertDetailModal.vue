<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<script lang="ts">
  export default defineComponent({
    name: 'AlertDetailModal',
  });
</script>
<script setup lang="ts" name="AlertDetailModal">
  import { defineComponent, reactive, ref, h } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { SvgIcon } from '/@/components/Icon';
  import { DescItem, Description } from '/@/components/Description';
  import { Divider, Tag, Typography } from 'ant-design-vue';
  const typographyParagraph = Typography.Paragraph;
  interface DingTalkType {
    token: string;
    contacts: string;
    isAtAll: boolean;
    alertDingURL: string;
    secretEnable: boolean;
    secretToken: string;
  }
  interface LarkType {
    token: string;
    isAtAll: boolean;
    secretEnable: boolean;
    secretToken: string;
  }

  const emailInfo = ref<{ contacts: string }>({ contacts: '' });
  const weChat = ref<{ contacts: string }>({ contacts: '' });
  const alertTypeTags = ref<string[]>([]);
  const dingTalk = reactive<Partial<DingTalkType>>({});
  const lark = reactive<Partial<LarkType>>({});

  const [registerModal] = useModalInner((data) => {
    emailInfo.value = { contacts: '' };
    weChat.value = { contacts: '' };
    alertTypeTags.value = [];
    if (data) {
      alertTypeTags.value = data.alertTypeTags;
      emailInfo.value = JSON.parse(data.emailParams || '{}');
      weChat.value = JSON.parse(data.weComParams || '{}');
      if (data.dingTalkParams) {
        Object.assign(dingTalk, JSON.parse(data.dingTalkParams || '{}'));
      }
      if (data.larkParams) {
        Object.assign(lark, JSON.parse(data.larkParams || '{}'));
        console.log('lark', lark);
      }
    }
  });
  const dingTalkColumn: DescItem[] = [
    { label: 'DingTalk Url', field: 'alertDingURL', span: 2, labelMinWidth: 50 },
    { label: 'Access Token', field: 'token', span: 2, render: renderTypl },
    { label: 'Secret Token', field: 'secretToken', span: 2, render: renderTypl },
    { label: 'DingTalk User', field: 'contacts' },
    { label: 'At All User', field: 'isAtAll', render: renderTag },
  ];
  const larkColumn: DescItem[] = [
    { label: 'Lark Token', field: 'token', span: 2, render: renderTypl },
    { label: 'Lark Secret Token', field: 'secretToken', span: 2, render: renderTypl },
    { label: 'At All User', field: 'isAtAll', render: renderTag },
  ];
  function renderTag(value: boolean) {
    return h(Tag, { color: value ? 'green' : 'red', class: '!leading-20px' }, () => String(value));
  }

  function renderTypl(value: string) {
    return h(typographyParagraph, { copyable: true, class: '!mb-0' }, () => value);
  }
</script>

<template>
  <BasicModal :show-ok-btn="false" @register="registerModal" class="alert-detail">
    <template #title>
      <SvgIcon name="alarm" size="25" />
      Alert Detail
    </template>
    <template v-if="alertTypeTags.includes('1')">
      <Divider>
        <SvgIcon name="mail" size="20" />
        E-mail
      </Divider>
      <Description
        class="alert-detail"
        :column="1"
        :data="emailInfo"
        :schema="[{ label: 'Alert Email', field: 'contacts' }]"
      />
    </template>
    <template v-if="alertTypeTags.includes('2')">
      <Divider>
        <SvgIcon name="dingtalk" size="20" />
        Ding Talk
      </Divider>
      <Description class="alert-detail" :column="2" :data="dingTalk" :schema="dingTalkColumn" />
    </template>
    <template v-if="alertTypeTags.includes('4')">
      <Divider><SvgIcon name="wecom" size="20" /> WeChat </Divider>
      <Description
        class="alert-detail"
        :column="1"
        :data="weChat"
        :schema="[{ label: 'WeChat token', field: 'token', render: renderTypl }]"
      />
    </template>
    <template v-if="alertTypeTags.includes('16')">
      <Divider>
        <SvgIcon name="dingtalk" size="20" />
        Ding Talk
      </Divider>
      <Description :column="2" :data="lark" :schema="larkColumn" class="alert-detail" />
    </template>
  </BasicModal>
</template>

<style>
  .alert-detail .ant-descriptions-item-label {
    width: 150px;
  }
</style>
