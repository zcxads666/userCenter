import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from 'umi';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: 'By zcxads666',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'uc',
          title: 'userCenter',
          href: 'https://github.com/zcxads666/userCenter',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined />zcxads666 Github</>,
          href: 'https://github.com/zcxads666',
          blankTarget: true,
        },
        {
          key: 'yg',
          title: '悠管管理系统',
          href: 'https://github.com/zcxads666/userCenter',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
