CREATE TABLE `screendata_sgcy_fbk` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `YWBK` varchar(100) COMMENT '业务板块',
    `BNWWC` double(10,2) COMMENT '本年完成数（亿元）',
    `SNTQS` double(10,2) COMMENT '上年同期数（亿元）',
    `TBZJL` double(10,2) COMMENT '同比增减率',
    `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收入分析-省管产业-分板块';


CREATE TABLE `screendata_sgcy_fdw` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `QY` varchar(100) COMMENT '区域',
    `YSMB` double(10,2) COMMENT '预算目标（亿元）',
    `BNWCS` double(10,2) COMMENT '本年完成数（亿元）',
    `SNTQS` double(10,2) COMMENT '上年同期数（亿元）',
    `YSWCL` varchar(500) DEFAULT NULL COMMENT '预算完成率',
    `TBZJL` varchar(500) DEFAULT NULL COMMENT '同比增减率',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收入分析-省管产业-分单位（区域与直属）';


CREATE TABLE `screendata_tzwcqk` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `BTZQYMC` varchar(100) COMMENT '被投资企业名称',
    `JHS` double(10,2) COMMENT '计划数（亿元）',
    `WCS` double(10,2) COMMENT '完成数（亿元）',
    `WCL` double(10,2) COMMENT '完成率',
    `TYPE` varchar(500) DEFAULT NULL COMMENT '1 省管产业股权投资表 2 省管产业固定资产投资 3 参控股投资表',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资完成情况';


CREATE TABLE `screendata_jsnsrfhqk_lrl` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `XM` varchar(100) COMMENT '项目',
    `ND` varchar(100) COMMENT '年份',
    `LRZE` double(10,2) COMMENT '利润总额（亿元）',
    `YYLRL` double(10,2) COMMENT '营业利润率',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='近三年收入与分红情况-省管产业-利润率';


CREATE TABLE `screendata_jsnsrfhqk_jzcsyl` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `XM` varchar(100) COMMENT '项目',
    `ND` varchar(100) COMMENT '年份',
    `JZC` double(10,2) COMMENT '净资产（亿元）',
    `JZCLSYL` double(10,2) COMMENT '净资产收益率',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='近三年收入与分红情况-省管产业-净资产收益率';



CREATE TABLE `screendata_jsnsrfhqk_tzhbqk` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `QYMC` varchar(100) COMMENT '企业名称',
    `TZCB` double(10,2) COMMENT '投资成本',
    `BNFH` double(10,2) COMMENT '本年分红',
    `LSLJFH` double(10,2) COMMENT '历年累计分红',
    `LJTZHBL` double(10,2) COMMENT '累计投资回报率',
    `NJTZHBL` double(10,2) COMMENT '年均投资回报率',
    `ND` varchar(100) COMMENT '年份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='近三年收入与分红情况-参控股企业投资回报情况表';



CREATE TABLE `screendata_htqdqk_sgcy_fbk` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `BK` varchar(100) COMMENT '板块',
    `BNHT` double(10,2) COMMENT '本年合同（亿元）',
    `SNHT` double(10,2) COMMENT '上年合同（亿元）',
    `TBZJL` double(10,2) COMMENT '同比增减率',
    `BNXTW` double(10,2) COMMENT '本年系统外',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同签订情况-省管产业-分板块';



CREATE TABLE `screendata_htqdqk_sgcy_fdw` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `DW` varchar(100) COMMENT '单位',
    `BNHT` double(10,2) COMMENT '本年合同（亿元）',
    `SNHT` double(10,2) COMMENT '上年合同（亿元）',
    `TBZJL` double(10,2) COMMENT '同比增减率',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同签订情况-省管产业-分单位';



CREATE TABLE `screendata_htqdqk_sgcy` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `FL` varchar(100) COMMENT '分类',
    `BQZ` double(10,2) COMMENT '本期值',
    `ZB` double(10,2) COMMENT '占比',
    `TB` double(10,2) COMMENT '同比',
    `TQZ` double(10,2) COMMENT '同期值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同签订情况-省管产业';



CREATE TABLE `screendata_zcfzl_ndzcfzb` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `XM` varchar(100) COMMENT '项目',
    `ZCZE` double(10,2) COMMENT '资产总额（亿元）',
    `ZCFZL` double(10,2) COMMENT '资产负债率',
    `ND` varchar(100) COMMENT '年份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产负债率-年度资产负债表';



CREATE TABLE `screendata_zcfzl_zcfzlqkb` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `QYMC` varchar(100) COMMENT '企业名称',
    `QMZCFZL` double(10,2) COMMENT '期末资产负债率',
    `NCZCFZL` double(10,2) COMMENT '年初资产负债率',
    `ZJL` double(10,2) COMMENT '较年初增减',
    `ND` varchar(100) COMMENT '年份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产负债率-资产负债率情况表';



CREATE TABLE `screendata_zcfzl_ljwcqkb` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `DWMC` varchar(100) COMMENT '单位',
    `QMYE` double(10,2) COMMENT '期末余额（亿元）',
    `JNCZJ` double(10,2) COMMENT '较年初增加（亿元）',
    `ZF` double(10,2) COMMENT '较年初增幅%',
    `ND` varchar(100) COMMENT '年份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产负债率-直属公司“两金”完成情况表';



CREATE TABLE `screendata_yszkjg` (
    `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `DWDWXZ` varchar(100) COMMENT '对外单位性质',
    `BQYE` double(10,2) COMMENT '本期余额',
    `SNTQYE` double(10,2) COMMENT '上年同期余额',
    `TBZZL` double(10,2) COMMENT '同比增长率',
    `ND` varchar(100) COMMENT '年份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应收账款结构-省公司参控股-应收账款结构';

