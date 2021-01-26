using System;
using Microsoft.EntityFrameworkCore;

namespace Orders.Models
{
    public class OrdersContext : DbContext
    {
        public OrdersContext(DbContextOptions options) : base(options)
        {
        }

        public DbSet<Order> Orders { get; set; }
    }
}
