package com.example.dwsj.bean;
/*
Created by xiaoyu on 2020/11/22

Describe: 主界面中动物世界的bean

*/


import java.util.List;

public class animaPagerBean {

    /**
     * code : 1
     * message : 成功
     * time : 2020-11-22T02:41:23.0970353
     * result : [{"id":4,"name":"中国龙","value":48,"valueRmb":8001,"remarks":"8001-24000","ranking":6,"profit":30,"cycle":15,"status":0},{"id":5,"name":"中国龙","value":48,"valueRmb":8001,"remarks":"8001-24000","ranking":6,"profit":30,"cycle":15,"status":0},{"id":6,"name":"白天鹅","value":10,"valueRmb":1201,"remarks":"1201-3600","ranking":8,"profit":5,"cycle":1,"status":0},{"id":7,"name":"旺财狗","value":15,"valueRmb":301,"remarks":"301-1200","ranking":4,"profit":15,"cycle":5,"status":0},{"id":8,"name":"金丝猴","value":16,"valueRmb":1201,"remarks":"1201-3600","ranking":3,"profit":15,"cycle":5,"status":0},{"id":9,"name":"东北虎","value":28,"valueRmb":3601,"remarks":"3601-8000","ranking":9,"profit":24,"cycle":12,"status":0},{"id":10,"name":"梅花鹿","value":26,"valueRmb":3601,"remarks":"3601-8000","ranking":2,"profit":19,"cycle":9,"status":0},{"id":11,"name":"赤兔马","value":22,"valueRmb":1201,"remarks":"1201-3600","ranking":7,"profit":20,"cycle":9,"status":0},{"id":12,"name":"报喜鸟","value":9,"valueRmb":301,"remarks":"301-1200","ranking":1,"profit":9,"cycle":3,"status":0},{"id":13,"name":"大熊猫","value":26,"valueRmb":3601,"remarks":"3601-8000","ranking":10,"profit":18,"cycle":9,"status":0},{"id":14,"name":"大红鹰","value":22,"valueRmb":3601,"remarks":"3601-8000","ranking":5,"profit":14,"cycle":7,"status":0},{"id":15,"name":"中国龙","value":48,"valueRmb":8001,"remarks":"8001-24000","ranking":6,"profit":30,"cycle":15,"status":0},{"id":16,"name":"白天鹅","value":10,"valueRmb":1201,"remarks":"1201-3600","ranking":8,"profit":5,"cycle":1,"status":0},{"id":17,"name":"旺财狗","value":15,"valueRmb":301,"remarks":"301-1200","ranking":4,"profit":15,"cycle":5,"status":0},{"id":18,"name":"金丝猴","value":16,"valueRmb":1201,"remarks":"1201-3600","ranking":3,"profit":15,"cycle":5,"status":0},{"id":19,"name":"东北虎","value":28,"valueRmb":3601,"remarks":"3601-8000","ranking":9,"profit":24,"cycle":12,"status":0},{"id":20,"name":"梅花鹿","value":26,"valueRmb":3601,"remarks":"3601-8000","ranking":2,"profit":19,"cycle":9,"status":0},{"id":21,"name":"赤兔马","value":22,"valueRmb":1201,"remarks":"1201-3600","ranking":7,"profit":20,"cycle":9,"status":0},{"id":22,"name":"报喜鸟","value":9,"valueRmb":301,"remarks":"301-1200","ranking":1,"profit":9,"cycle":3,"status":0},{"id":23,"name":"大熊猫","value":26,"valueRmb":3601,"remarks":"3601-8000","ranking":10,"profit":18,"cycle":9,"status":0},{"id":24,"name":"大红鹰","value":22,"valueRmb":3601,"remarks":"3601-8000","ranking":5,"profit":14,"cycle":7,"status":0},{"id":25,"name":"中国龙","value":48,"valueRmb":8001,"remarks":"8001-24000","ranking":6,"profit":30,"cycle":15,"status":0}]
     */

    private String code;
    private String message;
    private String time;
    /**
     * id : 4
     * name : 中国龙
     * value : 48
     * valueRmb : 8001
     * remarks : 8001-24000
     * ranking : 6
     * profit : 30
     * cycle : 15
     * status : 0
     */

    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int id;
        private String name;
        private int value;
        private int valueRmb;
        private String remarks;
        private int ranking;
        private int profit;
        private int cycle;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValueRmb() {
            return valueRmb;
        }

        public void setValueRmb(int valueRmb) {
            this.valueRmb = valueRmb;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getProfit() {
            return profit;
        }

        public void setProfit(int profit) {
            this.profit = profit;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
