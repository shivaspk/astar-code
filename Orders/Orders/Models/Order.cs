using System;
namespace Orders.Models
{
    public class Order
    {
        public Order()
        {
        }
        public int Id { get; set; }
        public string buyer_address { get; set; }
        public string buyer_name { get; set; }
        public string buyer_email { get; set; }
        public string buyer_phone { get; set; }
        public string create_time { get; set; }
        public string order_amount { get; set; }
        public string order_status { get; set; }
        public string update_time { get; set; }

    }
}
