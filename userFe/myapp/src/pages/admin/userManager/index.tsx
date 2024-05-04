import { PlusOutlined } from '@ant-design/icons';
import type {
  ProColumns,
  ProDescriptionsItemProps,
} from '@ant-design/pro-components';
import {
  ProCard,
  ProDescriptions,
  ProTable,

} from '@ant-design/pro-components';
import {Button, Image, Tabs,} from 'antd';
import { useState } from 'react';


import {searchUser} from "@/services/ant-design-pro/api";

const columns: ProColumns<API.CurrentUser>[] = [
  {
    title: 'Id',
    dataIndex: 'id',
    width: 64,
    valueType: 'indexBorder',

  },
  {
    title: '账户',
    dataIndex: 'accountnumber',
    copyable: true,
    ellipsis: true,
  },
  {
    title: '状态',
    dataIndex: 'status',
    ellipsis: true,
    valueType: 'select',
    valueEnum: {
      all: { text: '未封禁', status: 'Default' },
      0: {
        text: '正常用户',
        status: 'Success',
      },
      1: {
        text: '已封禁',
        status: 'Error',
      },
    },
  },
  {
    title: '权限',
    dataIndex: 'permissions',
    ellipsis: true,
    valueType: 'select',
      valueEnum: {
        all: { text: '普通用户', status: 'Default' },
        0: {
          text: '普通用户',
          status: 'Default',
        },
        1: {
          text: '管理员',
          status: 'Success',
        },
      },
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    render: (_, record) => (
    <div>
      <Image src={record.avatar} width={40}/>
    </div>
      ),
    ellipsis: true,
  },
  {
    title: '昵称',
    dataIndex: 'nickname',
    copyable: true,
    ellipsis: true,
  },
  {
    title: '性别',
    dataIndex: 'gender',
    ellipsis: true,
    valueType: 'select',
    valueEnum: {
      0: {
        text: '男性',
        //status: 'Success',
      },
      1: {
        text: '女性',
        //status: 'Error',
      },
      3: {
        text: '保密',
        //status: 'Error',
      },
    },
  },
  // {
  //   title: (_, type) => (type === 'table' ? '状态' : '列表状态'),
  //   dataIndex: 'state',
  //   initialValue: 'all',
  //   filters: true,
  //   onFilter: true,
  //   valueType: 'select',
  //   valueEnum: {
  //     all: { text: '全部', status: 'Default' },
  //     open: {
  //       text: '未解决',
  //       status: 'Error',
  //     },
  //     closed: {
  //       text: '已解决',
  //       status: 'Success',
  //     },
  //   },
  // },
  // {
  //   title: '排序方式',
  //   key: 'direction',
  //   hideInTable: true,
  //   hideInDescriptions: true,
  //   dataIndex: 'direction',
  //   filters: true,
  //   onFilter: true,
  //   valueType: 'select',
  //   valueEnum: {
  //     asc: '正序',
  //     desc: '倒序',
  //   },
  // },
  // {
  //   title: '标签',
  //   dataIndex: 'labels',
  //   width: 120,
  //   render: (_, row) => (
  //     <Space>
  //       {row.labels.map(({ name, color }) => (
  //         <Tag color={color} key={name}>
  //           {name}
  //         </Tag>
  //       ))}
  //     </Space>
  //   ),
  // },
  // {
  //   title: 'option',
  //   valueType: 'option',
  //   dataIndex: 'id',
  //   render: (text, row) => [
  //     <a href={row.url} key="show" target="_blank" rel="noopener noreferrer">
  //       查看
  //     </a>,
  //     <TableDropdown
  //       key="more"
  //       onSelect={(key) => message.info(key)}
  //       menus={[
  //         { key: 'copy', name: '复制' },
  //         { key: 'delete', name: '删除' },
  //       ]}
  //     />,
  //   ],
  // },
];

export default () => {
  const [type, setType] = useState('table');
  return (
    <ProCard>
      <Tabs activeKey={type} onChange={(e) => setType(e)}>
        <Tabs.TabPane tab="table" key="table" />
        <Tabs.TabPane tab="form" key="form" />
        <Tabs.TabPane tab="descriptions" key="descriptions" />
      </Tabs>
      {['table', 'form'].includes(type) && (
        <ProTable<API.CurrentUser>
          columns={columns}
          type={type as 'table'}
          request={async (params) => {

             const userList = await searchUser();
             //console.log(userList);
            return {

               data: userList
            }
          }
          }
          // request={
          //   async (params = {} )=>
          //   request<{
          //   data: API.CurrentUser[];
          // }>('/api/user/search', {
          //   params,
          // })
          // }
           pagination={{
             pageSize: 5,
           }}
          rowKey="id"
          dateFormatter="string"
          headerTitle="查询 Table"
          toolBarRender={() => [
            <Button key="3" type="primary">
              <PlusOutlined />
              新建
            </Button>,
          ]}
        />
      )}
      {type === 'descriptions' && (
        <ProDescriptions
          style={{
            background: '#fff',
          }}
          columns={columns as ProDescriptionsItemProps<API.CurrentUser>[]}
          request={async (params) => {

            // const userList = await searchUser();
            // console.log(userList);
            return {

              // data: userList
            }
            }
          }
            // const msg = await request<{
            //   data: CurrentUser[];
            // }>('/api/user/search', {
            //   params,
            // });
            // return {
            //   ...msg,
            //   data: msg?.data[0],
            // };

        />
      )}
    </ProCard>
  );
};
